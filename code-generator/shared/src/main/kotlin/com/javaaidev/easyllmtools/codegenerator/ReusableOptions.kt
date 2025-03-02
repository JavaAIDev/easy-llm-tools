package com.javaaidev.easyllmtools.codegenerator

import picocli.CommandLine
import picocli.CommandLine.Command
import java.io.File

@Command
class ReusableOptions {
    @CommandLine.Parameters(index = "0")
    lateinit var inputSpec: File

    @CommandLine.Option(
        names = ["--group-id"],
        defaultValue = "com.javaaidev.easyllmtools.tools",
        description = ["Group id"]
    )
    var groupId: String = ""

    @CommandLine.Option(
        names = ["--artifact-id"],
        defaultValue = "my-tool",
        description = ["Artifact id"]
    )
    var artifactId: String = ""

    @CommandLine.Option(
        names = ["--artifact-version"],
        defaultValue = "1.0.0",
        description = ["Artifact version"]
    )
    var artifactVersion: String = ""

    @CommandLine.Option(
        names = ["--package-name"],
        defaultValue = "com.javaaidev.easyllmtools.tools.mytool",
        description = ["Package name"]
    )
    var packageName: String = ""

    @CommandLine.Option(
        names = ["--output"],
        defaultValue = "./output",
        description = ["Output directory"]
    )
    lateinit var outputDir: File
}