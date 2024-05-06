package com.logparser.logs;

import lombok.Data;

@Data
public class applicationLog implements log {
    private String timestamp;
    private String type;
    private String level;
    private String message; // Specific to Application logs

    public applicationLog(String timestamp, String type, String level, String message) {
        this.timestamp = timestamp;
        this.type = type;
        this.level = level;
        this.message = message;
    }

    @Override
    public void accept(logVisitor visitor) {
        visitor.visit(this);
    }

}