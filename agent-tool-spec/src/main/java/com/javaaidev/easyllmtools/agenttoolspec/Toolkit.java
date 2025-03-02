package com.javaaidev.easyllmtools.agenttoolspec;

public interface Toolkit {

    String getName();

    default String getDescription() {
        return this.getName();
    }
}
