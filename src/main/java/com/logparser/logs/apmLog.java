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
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
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
                    case "webmount":
                        this.webmount = keyValue[1];
                        break;
                    case "value":
                        this.value = Integer.parseInt(keyValue[1]);
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
