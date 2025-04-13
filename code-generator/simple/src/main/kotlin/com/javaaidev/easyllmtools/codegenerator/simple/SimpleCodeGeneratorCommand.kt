package com.javaaidev.easyllmtools.codegenerator.simple

import com.javaaidev.easyllmtools.codegenerator.ReusableOptions
import picocli.CommandLine
import picocli.CommandLine.Command
import java.util.concurrent.Callable

@Command(name = "simple", description = ["Generate simple tool implementation"])
class SimpleCodeGeneratorCommand : Callable<Int> {
    @CommandLine.Mixin
    lateinit var options: ReusableOptions

    @CommandLine.Option(
        names = ["--overwrite-tool-implementation"],
        defaultValue = "false",
        description = ["Should tool implementation be overwritten"]
    )
    var overwriteToolImplementation: Boolean = false

    @CommandLine.Option(
        names = ["--overwrite-pom-file"],
        defaultValue = "false",
        description = ["Should pom file be overwritten"]
    )
    var overwritePomFile: Boolean = false

    @CommandLine.Option(
        names = ["--tool-id-prefix"],
        defaultValue = "",
        description = ["Prefix of generated tool id"]
    )
    var toolIdPrefix = ""

    @CommandLine.Option(
        names = ["--model-files-only"],
        defaultValue = "false",
        description = ["Only generate model files"]
    )
    var modelFilesOnly: Boolean = false

    @CommandLine.Option(
        names = ["--no-model-files"],
        defaultValue = "false",
        description = ["Skip generation of model files"]
    )
    var noModelFiles: Boolean = false

    @CommandLine.Option(
        names = ["--tool-name"],
        defaultValue = "",
        description = ["Override tool name in the spec file"]
    )
    var toolName: String = ""

    @CommandLine.Option(
        names = ["--tool-description"],
        defaultValue = "",
        description = ["Override tool description in the spec file"]
    )
    var toolDescription: String = ""

    @CommandLine.Option(
        names = ["--model-package-name"],
        defaultValue = "",
        description = ["Override default model package name"]
    )
    var modelPackageName: String = ""

    override fun call(): Int {
        SimpleCodeGenerator.generate(
            options.inputSpec,
            options.groupId,
            options.artifactId,
            options.artifactVersion,
            options.packageName,
            options.outputDir.toPath(),
            overwriteToolImplementation,
            overwritePomFile,
            toolIdPrefix,
            modelFilesOnly,
            noModelFiles,
            options.parentGroupId,
            options.parentArtifactId,
            options.parentArtifactVersion,
            toolName,
            toolDescription,
            modelPackageName,
        )
        return 0
    }

}