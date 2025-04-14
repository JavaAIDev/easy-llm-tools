package com.javaaidev.easyllmtools.codegenerator.cli

import com.javaaidev.easyllmtools.codegenerator.simple.SimpleCodeGeneratorCommand
import com.javaaidev.easyllmtools.generator.openapi.OpenAPICodeGeneratorCommand
import picocli.CommandLine
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "easy-llm-tools",
    mixinStandardHelpOptions = true,
    version = ["0.1.9"],
    description = ["Generate code for LLM tools"],
    scope = CommandLine.ScopeType.INHERIT,
    subcommands = [
        SimpleCodeGeneratorCommand::class,
        OpenAPICodeGeneratorCommand::class
    ],
)
class CodeGeneratorCli {
}

fun main(args: Array<String>) {
    exitProcess(CommandLine(CodeGeneratorCli()).execute(*args))
}