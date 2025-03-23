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
            options.parentGroupId,
            options.parentArtifactId,
            options.parentArtifactVersion,
        )
        return 0
    }

}