package com.javaaidev.easyllmtools.agenttoolspec;

public interface ToolDefinition {

    default String getId() {
        return this.getName();
    }

    String getName();

    String getDescription();

    default String getParametersSchema() {
        return "{}";
    }

    default String getReturnTypeSchema() {
        return "{}";
    }

    default String getExamples() {
        return "{}";
    }
}
