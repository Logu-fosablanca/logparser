package com.logparser.logs;


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

    @Override
    public void accept(logVisitor visitor) {
        visitor.visit(this);
    }


}
