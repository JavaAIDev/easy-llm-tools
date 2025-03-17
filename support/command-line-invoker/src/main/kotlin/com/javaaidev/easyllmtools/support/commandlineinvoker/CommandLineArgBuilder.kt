package com.javaaidev.easyllmtools.support.commandlineinvoker

interface CommandLineArgBuilder {
    fun toArgs(): List<String>
    fun order() = 0
}

data class FixedValueCommandLineArgBuilder(
    val arg: String,
    val order: Int = 0,
) : CommandLineArgBuilder {
    override fun toArgs() = listOf(arg)
    override fun order() = order
}

data class NameValueCommandLineArgBuilder(
    val name: String,
    val value: String,
    val separator: String = "=",
    val order: Int = 0,
) : CommandLineArgBuilder {
    override fun toArgs() = when (separator) {
        " " -> listOf(name, value)
        else -> listOf("$name$separator$value")
    }

    override fun order() = order
}

data class BooleanCommandLineArgBuilder(
    val value: Boolean,
    val trueArg: String,
    val falseArg: String = "",
    val order: Int = 0,
) : CommandLineArgBuilder {
    override fun toArgs() = listOf(if (value) trueArg else falseArg)
    override fun order() = order
}