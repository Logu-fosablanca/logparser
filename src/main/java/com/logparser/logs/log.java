package com.logparser.logs;

import com.logparser.visitors.logVisitor;

public interface log {
    void accept(logVisitor visitor);
}
