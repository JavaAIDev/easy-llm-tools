package com.javaaidev.easyllmtools.support.commandlineinvoker

import org.junit.jupiter.api.Test
import kotlin.test.DefaultAsserter.assertEquals

class CommandLineRunnerTest {
    @Test
    fun testCurl() {
        val spec = CommandLineExecutionSpec(
            "curl",
            listOf(
                FixedValueCommandLineArgBuilder("-L"),
                NameValueCommandLineArgBuilder("-o", "/tmp/output", " "),
                FixedValueCommandLineArgBuilder("http://www.example.org")
            ),
            mapOf(),
        )
        val result = CommandLineRunner.execute(spec)
        assertEquals(null, 0, result.exitValue)
    }
}