package com.logparser.logs;


import com.google.gson.Gson;
import com.logparser.visitors.logVisitor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class apmLog implements log {
    private String timestamp;
    private String metric;
    private String host;
    private String webmount; // Add webmount variable
    private int value;

    // Constructor
    public apmLog(String timestamp, String metric, String host, int value) {
        this.timestamp = timestamp;
        this.metric = metric;
        this.host = host;
        this.value = value;
    }

    public apmLog(String line){
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
                    case "metric":
                        this.metric = keyValue[1];
                        break;
                    case "host":
                        this.host = keyValue[1];
                        break;
                    case "webmount": // Assuming "webmount" is the correct variable name
                        this.webmount = keyValue[1];
                        break;
                    case "value":
                        this.value = Integer.parseInt(keyValue[1]); // Parse value as integer
                        break;
                    default:
                        // Handle unknown key or ignore
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
