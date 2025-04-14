package com.javaaidev.easyllmtools.codegenerator.simple

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jknack.handlebars.EscapingStrategy
import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Helper
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import com.javaaidev.easyllmtools.codegenerator.InvalidToolSpecException
import com.sun.codemodel.JCodeModel
import org.apache.commons.text.CaseUtils
import org.apache.commons.text.StringEscapeUtils
import org.jsonschema2pojo.*
import org.jsonschema2pojo.rules.RuleFactory
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Supplier
import kotlin.io.path.exists

data class Definition(
    val id: String,
    val name: String,
    val description: String,
    val parametersSchema: String,
    val returnTypeSchema: String,
    val examples: String,
)

data class TemplateContext(
    val groupId: String,
    val artifactId: String,
    val artifactVersion: String,
    val hasParentProject: Boolean,
    val parentGroupId: String,
    val parentArtifactId: String,
    val parentArtifactVersion: String,
    val packageName: String,
    val specPackageName: String,
    val generatedSpecPackageName: String,
    val toolClassName: String,
    val hasConfiguration: Boolean,
    val configurationClassName: String,
    val parametersClassName: String,
    val returnTypeClassName: String,
    val toolFactoryClassName: String,
    val definition: Definition,
)

data class GeneratedFile(
    val templateName: String,
    val dir: Path,
    val overwrite: Boolean = true,
    val skip: Boolean = false,
    val fileNameSupplier: Supplier<String>,
)

object SimpleCodeGenerator {
    private val objectMapper = ObjectMapper()
    private val handlebars: Handlebars
    private val logger = LoggerFactory.getLogger(SimpleCodeGenerator::class.java)

    init {
        val loader = ClassPathTemplateLoader()
        loader.prefix = "/template"
        handlebars = Handlebars(loader).with(EscapingStrategy.NOOP)
        handlebars.registerHelper("escapeJava", Helper<String> { context, _ ->
            StringEscapeUtils.escapeJava(context)
        })
    }

    fun generate(
        inputSpec: File,
        groupId: String,
        artifactId: String,
        artifactVersion: String,
        packageName: String,
        outputDir: Path,
        overwriteToolImplementation: Boolean,
        overwritePomFile: Boolean,
        toolIdPrefix: String,
        modelFilesOnly: Boolean,
        noModelFiles: Boolean,
        parentGroupId: String,
        parentArtifactId: String,
        parentArtifactVersion: String,
        customToolName: String,
        llmToolName: String,
        toolDescription: String,
        customModelPackageName: String,
    ) {
        logger.info("Generate code from {}", inputSpec.toPath().toAbsolutePath().normalize())
        val jsonNode = objectMapper.readTree(inputSpec)
        val definitionNode =
            jsonNode.get("definition") ?: throw InvalidToolSpecException("Definition is a required")
        val name =
            customToolName.ifBlank { definitionNode.get("name")?.textValue() ?: "" }
        val description =
            toolDescription.ifBlank { definitionNode.get("description")?.textValue() ?: "" }
        val output = Files.createTempDirectory("tool_")
        val sourceRootDir = outputDir.resolve(Path.of("src", "main", "java"))
        val resourcesRootDir = outputDir.resolve(Path.of("src", "main", "resources"))
        Files.createDirectories(sourceRootDir)
        Files.createDirectories(resourcesRootDir)
        val parametersJson = jsonNodeString(definitionNode.get("parameters"))
        val returnTypeJson = jsonNodeString(definitionNode.get("returnType"))
        val examplesJson = jsonNodeString(definitionNode.get("examples"))
        val configurationJson = jsonNodeString(jsonNode.get("configuration"))
        val modelPackageName = customModelPackageName.ifBlank { "$packageName.model" }
        val toolName = CaseUtils.toCamelCase(name, true)
        val toolId = toolIdPrefix + (definitionNode.get("id")?.textValue() ?: toolName)
        val hasConfiguration = notEmptyJson(configurationJson)
        val configurationClassName =
            if (hasConfiguration) "${toolName}Configuration" else "Void"
        val parametersClassName =
            if (notEmptyJson(parametersJson)) "${toolName}Parameters" else "Void"
        val returnTypeClassName =
            if (notEmptyJson(returnTypeJson)) "${toolName}ReturnType" else "Void"
        if (!noModelFiles && notEmptyJson(parametersJson)) {
            jsonNodeToCode(
                output,
                sourceRootDir,
                parametersJson,
                parametersClassName,
                modelPackageName
            )
        }
        if (!noModelFiles && notEmptyJson(returnTypeJson)) {
            jsonNodeToCode(
                output,
                sourceRootDir,
                returnTypeJson,
                returnTypeClassName,
                modelPackageName
            )
        }
        if (!noModelFiles && hasConfiguration) {
            jsonNodeToCode(
                output,
                sourceRootDir,
                configurationJson,
                configurationClassName,
                modelPackageName
            )
        }
        val hasParentProject =
            parentGroupId.isNotEmpty() && parentArtifactId.isNotEmpty() && parentArtifactVersion.isNotEmpty()
        val context = TemplateContext(
            groupId,
            artifactId,
            artifactVersion,
            hasParentProject,
            parentGroupId,
            parentArtifactId,
            parentArtifactVersion,
            packageName,
            "com.javaaidev.easyllmtools.llmtoolspec",
            modelPackageName,
            toolName,
            hasConfiguration,
            configurationClassName,
            parametersClassName,
            returnTypeClassName,
            if (hasConfiguration) "ConfigurableToolFactory" else "UnconfigurableToolFactory",
            Definition(
                toolId,
                llmToolName.ifBlank { toolName },
                description,
                parametersJson,
                returnTypeJson,
                examplesJson,
            ),
        )

        val sourceDir = sourceRootDir.resolve(Path.of(".", *packageName.split(".").toTypedArray()))
        val servicesDir = resourcesRootDir.resolve("META-INF").resolve("services")
        val files = listOf(
            GeneratedFile(
                "abstractTool",
                sourceDir,
                skip = modelFilesOnly
            ) { "Abstract${toolName}.java" },
            GeneratedFile(
                "toolFactory",
                sourceDir,
                skip = modelFilesOnly
            ) { "${toolName}Factory.java" },
            GeneratedFile(
                "tool",
                sourceDir,
                overwriteToolImplementation,
                modelFilesOnly
            ) { "${toolName}.java" },
            GeneratedFile("pom", outputDir, overwritePomFile) { "pom.xml" },
            GeneratedFile(
                "services",
                servicesDir,
                skip = modelFilesOnly,
            ) { "com.javaaidev.easyllmtools.llmtoolspec.ToolFactory" },
        )

        files.forEach { file ->
            Files.createDirectories(file.dir)
            val outputFile = file.dir.resolve(file.fileNameSupplier.get())
            if (file.skip || (outputFile.exists() && !file.overwrite)) {
                logger.info("Skip generation of file {}", outputFile)
                return@forEach
            }
            val template = handlebars.compile(file.templateName)
            template.apply(context).run {
                logger.info("Created file {}", outputFile.toAbsolutePath().normalize())
                Files.writeString(outputFile, this)
            }
        }

    }

    private fun jsonNodeString(jsonNode: JsonNode?): String {
        return jsonNode?.let {
            try {
                objectMapper.writeValueAsString(it)
            } catch (e: Exception) {
                "{}"
            }
        } ?: "{}"
    }


    private fun jsonNodeToCode(
        output: Path,
        codePath: Path,
        json: String,
        className: String,
        packageName: String
    ) {
        val file = output.resolve("$className.json")
        Files.writeString(file, json)
        generateCode(file, codePath, className, packageName)
    }

    private fun generateCode(
        schemaFile: Path,
        output: Path,
        className: String,
        packageName: String
    ) {
        val codeModel = JCodeModel()
        val source = schemaFile.toUri().toURL()
        val config = object : DefaultGenerationConfig() {
            override fun isGenerateBuilders(): Boolean {
                return true
            }

            override fun isUseInnerClassBuilders(): Boolean {
                return true
            }

            override fun getSourceType(): SourceType {
                return SourceType.JSONSCHEMA
            }

            override fun isIncludeAdditionalProperties(): Boolean {
                return false
            }

            override fun isIncludeConstructors(): Boolean {
                return true
            }

            override fun isIncludeRequiredPropertiesConstructor(): Boolean {
                return true
            }

            override fun isIncludeAllPropertiesConstructor(): Boolean {
                return true
            }

            override fun isIncludeGeneratedAnnotation(): Boolean {
                return false
            }
        }

        val mapper =
            SchemaMapper(RuleFactory(config, NoopAnnotator(), SchemaStore()), SchemaGenerator())
        mapper.generate(codeModel, className, packageName, source)

        Files.createDirectories(output)
        codeModel.build(output.toFile())
    }

    private fun notEmptyJson(json: String) = json.isNotEmpty() || json != "{}"
}