package com.logparser.logs;

import com.google.gson.Gson;
import com.logparser.visitors.logVisitor;
import lombok.Data;

@Data
public class applicationLog implements log {
    private String timestamp;
    private String type;
    private String level;
    private String message;

    public applicationLog(String timestamp, String type, String level, String message) {
        this.timestamp = timestamp;
        this.type = type;
        this.level = level;
        this.message = message;
    }

    public applicationLog (String line) {
        String[] parts = line.split("\\s+");
        // Iterate through the parts to extract information
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
                    case "type":
                        this.type = keyValue[1];
                        break;
                    case "level":
                        this.level = keyValue[1];
                        break;
                    case "message":
                        this.message = keyValue[1];
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

    // Override toString() method to return JSON representation
    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public void accept(logVisitor visitor) {
        visitor.visit(this);
    }

}