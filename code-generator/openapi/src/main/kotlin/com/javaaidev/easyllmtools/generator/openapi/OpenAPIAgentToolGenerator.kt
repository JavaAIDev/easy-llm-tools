package com.javaaidev.easyllmtools.generator.openapi

import org.openapitools.codegen.CodegenConfig
import org.openapitools.codegen.SupportingFile
import org.openapitools.codegen.languages.JavaClientCodegen

class OpenAPIAgentToolGenerator : JavaClientCodegen(), CodegenConfig {
    init {
        library = "okhttp-gson"
        apiTemplateFiles["agentToolkit.mustache"] = "AgentToolkit.java"
        apiTemplateFiles["toolConfiguration.mustache"] = "ToolConfiguration.java"
        supportingFiles.add(
            SupportingFile(
                "serviceFactories.mustache",
                "src/main/resources/META-INF/services",
                "com.javaaidev.easyllmtools.agenttoolspec.ToolFactory"
            )
        )
    }

    override fun getName(): String {
        return "Java"
    }
}