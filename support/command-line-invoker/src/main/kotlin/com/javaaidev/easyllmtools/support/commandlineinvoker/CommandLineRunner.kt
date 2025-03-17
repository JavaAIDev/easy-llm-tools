package com.javaaidev.easyllmtools.support.commandlineinvoker

import org.buildobjects.process.ProcBuilder
import java.time.Duration

class CommandLineExecutionSpec(
    val command: String,
    val argBuilders: List<CommandLineArgBuilder>?,
    val env: Map<String, String>?,
    val timeout: Duration? = Duration.ofSeconds(30)
)

class CommandLineExecutionResult(
    val exitValue: Int,
    val successOutput: String?,
    val errorOutput: String?
)

object CommandLineRunner {
    fun execute(spec: CommandLineExecutionSpec): CommandLineExecutionResult {
        val builder = ProcBuilder(spec.command)
        spec.argBuilders?.sortedBy { it.order() }?.let {
            builder.withArgs(*it.flatMap { argBuilder -> argBuilder.toArgs() }.toTypedArray())
        }
        spec.env?.let {
            builder.withVars(it)
        }
        if (spec.timeout != null) {
            builder.withTimeoutMillis(spec.timeout.toMillis())
        } else {
            builder.withNoTimeout()
        }
        val result = builder.run()
        return CommandLineExecutionResult(result.exitValue, result.outputString, result.errorString)
    }
}