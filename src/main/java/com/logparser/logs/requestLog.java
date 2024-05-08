package com.logparser.logs;

import com.google.gson.Gson;
import com.logparser.visitors.logVisitor;
import lombok.Data;

@Data
public class requestLog implements log {
    private String timestamp;
    private String requestMethod;
    private String requestUrl;
    private int responseStatusCode;
    private int responseTimeMs;

    public requestLog(String timestamp, String type, String level, String requestMethod,
                      String requestUrl, int responseStatusCode, int responseTimeMs) {
        this.timestamp = timestamp;
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.responseStatusCode = responseStatusCode;
        this.responseTimeMs = responseTimeMs;
    }

    public requestLog(String line){
        String[] parts = line.split("\\s+");

        for (String part : parts) {
            // Split each part by '=' to separate key and value
            String[] keyValue = part.split("=");

            // Check if keyValue length is 2 to ensure it's in key=value format
            if (keyValue.length == 2) {
                // Extract information based on the key
                switch (keyValue[0]) {
                    case "timestamp":
                        this.timestamp = keyValue[1];
                        break;
                    case "request_method":
                        this.requestMethod = keyValue[1];
                        break;
                    case "request_url":
                        this.requestUrl = keyValue[1];
                        break;
                    case "response_status":
                        this.responseStatusCode = Integer.parseInt(keyValue[1]);
                        break;
                    case "response_time_ms":
                        this.responseTimeMs = Integer.parseInt(keyValue[1]);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public void accept(logVisitor visitor) {
        visitor.visit(this);
    }


}