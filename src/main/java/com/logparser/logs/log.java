package com.logparser.logs;

public interface log {
    void accept(logVisitor visitor);
}
