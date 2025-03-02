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
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Supplier

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
    val packageName: String,
    val specPackageName: String,
    val generatedSpecPackageName: String,
    val toolClassName: String,
    val configurationClassName: String,
    val parametersClassName: String,
    val returnTypeClassName: String,
    val definition: Definition,
)

data class GeneratedFile(
    val templateName: String,
    val dir: Path,
    val fileNameSupplier: Supplier<String>,
)

object SimpleCodeGenerator {
    private val objectMapper = ObjectMapper()
    private val handlebars: Handlebars

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
        outputDir: Path
    ) {
        val jsonNode = objectMapper.readTree(inputSpec)
        val definitionNode =
            jsonNode.get("definition") ?: throw InvalidToolSpecException("Definition is a required")
        val name = definitionNode.get("name")?.textValue() ?: ""
        val id = definitionNode.get("id")?.textValue() ?: name
        val description = definitionNode.get("description")?.textValue() ?: ""
        val output = Files.createTempDirectory("tool_")
        val sourceRootDir = outputDir.resolve(Path.of("src", "main", "java"))
        val resourcesRootDir = outputDir.resolve(Path.of("src", "main", "resources"))
        Files.createDirectories(sourceRootDir)
        Files.createDirectories(resourcesRootDir)
        val parametersJson = jsonNodeString(definitionNode.get("parameters"))
        val returnTypeJson = jsonNodeString(definitionNode.get("returnType"))
        val examplesJson = jsonNodeString(definitionNode.get("examples"))
        val configurationJson = jsonNodeString(jsonNode.get("configuration"))
        val modelPackageName = "$packageName.model"
        val toolName = CaseUtils.toCamelCase(name, true)
        val configurationClassName = "${toolName}Configuration"
        val parametersClassName = "${toolName}Parameters"
        val returnTypeClassName = "${toolName}ReturnType"
        jsonNodeToCode(output, sourceRootDir, parametersJson, parametersClassName, modelPackageName)
        jsonNodeToCode(output, sourceRootDir, returnTypeJson, returnTypeClassName, modelPackageName)
        jsonNodeToCode(
            output,
            sourceRootDir,
            configurationJson,
            configurationClassName,
            modelPackageName
        )
        val context = TemplateContext(
            groupId,
            artifactId,
            artifactVersion,
            packageName,
            "com.javaaidev.easyllmtools.agenttoolspec",
            modelPackageName,
            toolName,
            configurationClassName,
            parametersClassName,
            returnTypeClassName,
            Definition(
                toolName,
                toolName,
                description,
                parametersJson,
                returnTypeJson,
                examplesJson,
            )
        )

        val sourceDir = sourceRootDir.resolve(Path.of(".", *packageName.split(".").toTypedArray()))
        val servicesDir = resourcesRootDir.resolve("META-INF").resolve("services")
        Files.createDirectories(servicesDir)
        val files = listOf(
            GeneratedFile("abstractTool", sourceDir) { "Abstract${toolName}.java" },
            GeneratedFile("toolFactory", sourceDir) { "${toolName}Factory.java" },
            GeneratedFile("tool", sourceDir) { "${toolName}.java" },
            GeneratedFile("pom", outputDir) { "pom.xml" },
            GeneratedFile(
                "services",
                servicesDir
            ) { "com.javaaidev.easyllmtools.agenttoolspec.ToolFactory" },
        )

        files.forEach { file ->
            val template = handlebars.compile(file.templateName)
            template.apply(context).run {
                Files.writeString(file.dir.resolve(file.fileNameSupplier.get()), this)
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
}