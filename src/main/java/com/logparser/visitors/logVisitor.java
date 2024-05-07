package com.logparser.visitors;

import com.logparser.logs.apmLog;
import com.logparser.logs.applicationLog;
import com.logparser.logs.requestLog;

public interface logVisitor {
    void visit(apmLog log);
    void visit(applicationLog log);
    void visit(requestLog log);
}
