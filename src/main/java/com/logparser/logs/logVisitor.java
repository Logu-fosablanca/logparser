package com.logparser.logs;

public interface logVisitor {
    void visit(apmLog log);

    // Visit method for Application logs
    void visit(applicationLog log);

    // Visit method for Request logs
    void visit(requestLog log);


}
