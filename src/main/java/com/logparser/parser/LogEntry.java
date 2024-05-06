package com.logparser.parser;

import lombok.Data;

@Data
public class LogEntry {
    private String timestamp;
    private String type; // Type of log: APM, Application, Request
    private String level; // Level of log: INFO, ERROR, etc.
    private String message;
    private String requestMethod; // Additional fields specific to Request logs

    public LogEntry(String timestamp, String type, String level, String message, String requestMethod) {
        this.timestamp = timestamp;
        this.type = type;
        this.level = level;
        this.message = message;
        this.requestMethod = requestMethod;
    }

    public LogEntry() {

    }
}
