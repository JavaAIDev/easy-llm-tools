package com.javaaidev.easyllmtools.generator.openapi

import com.javaaidev.easyllmtools.codegenerator.ReusableOptions
import org.openapitools.codegen.ClientOptInput
import org.openapitools.codegen.DefaultGenerator
import org.openapitools.codegen.config.CodegenConfigurator
import org.slf4j.LoggerFactory
import picocli.CommandLine
import java.io.File
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "openapi",
    description = ["Generate LLM tools from OpenAPI spec"]
)
class OpenAPICodeGeneratorCommand : Callable<Int> {

    @CommandLine.Mixin
    lateinit var options: ReusableOptions

    private val logger = LoggerFactory.getLogger(OpenAPICodeGeneratorCommand::class.java)

    @CommandLine.Option(
        names = ["--validate-spec"],
        defaultValue = "true",
        description = ["Should the spec be validated"]
    )
    var validateSpec: Boolean = true


    override fun call(): Int {
        val packageName = options.packageName
        val configurator: CodegenConfigurator = CodegenConfigurator()
            .setGroupId(options.groupId)
            .setArtifactId(options.artifactId)
            .setArtifactVersion(options.artifactVersion)
            .setPackageName(packageName)
            .setInvokerPackage(packageName)
            .setModelPackage("$packageName.model")
            .setApiPackage("$packageName.api")
            .setGeneratorName("Java")
            .addAdditionalProperty("useEnumCaseInsensitive", true)
            .addAdditionalProperty("failOnUnknownProperties", false)
            .addAdditionalProperty("serializationLibrary", "jackson")
            .addAdditionalProperty("disallowAdditionalPropertiesIfNotPresent", "false")
            .setValidateSpec(validateSpec)
            .setInputSpec(fileToString(options.inputSpec))
            .setOutputDir(fileToString(options.outputDir))

        val clientOptInput: ClientOptInput = configurator.toClientOptInput()
        val generator = DefaultGenerator()
        generator.opts(clientOptInput).generate()
        return 0
    }

    private fun fileToString(file: File) = file.toPath().toAbsolutePath().normalize().toString()
}