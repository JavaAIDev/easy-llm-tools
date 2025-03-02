package com.javaaidev.easyllmtools.codegenerator.simple

import com.javaaidev.easyllmtools.codegenerator.ReusableOptions
import picocli.CommandLine
import picocli.CommandLine.Command
import java.util.concurrent.Callable

@Command(name = "simple", description = ["Generate simple tool implementation"])
class SimpleCodeGeneratorCommand : Callable<Int> {
    @CommandLine.Mixin
    lateinit var options: ReusableOptions

    override fun call(): Int {
        SimpleCodeGenerator.generate(
            options.inputSpec,
            options.groupId,
            options.artifactId,
            options.artifactVersion,
            options.packageName,
            options.outputDir.toPath()
        )
        return 0
    }

}