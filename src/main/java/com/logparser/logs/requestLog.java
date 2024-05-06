package com.logparser.logs;

import lombok.Data;

@Data
public class requestLog implements log {
    private String timestamp;
    private String type;
    private String level;
    private String requestMethod;
    private String requestUrl;
    private int responseStatusCode;
    private int responseTimeMs;

    public requestLog(String timestamp, String type, String level, String requestMethod,
                      String requestUrl, int responseStatusCode, int responseTimeMs) {
        this.timestamp = timestamp;
        this.type = type;
        this.level = level;
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.responseStatusCode = responseStatusCode;
        this.responseTimeMs = responseTimeMs;
    }

    @Override
    public void accept(logVisitor visitor) {
        visitor.visit(this);
    }


}