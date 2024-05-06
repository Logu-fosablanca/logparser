package com.logparser.aggregators;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.HashMap;

@Data
@Getter
@Setter
public class requestAggregation {
    private String url;
    private int minResponseTime;
    private int maxResponseTime;
    private double averageResponseTime;
    private double medianResponseTime;
    private Map<String, Integer> statusCodeCounts;

    public requestAggregation() {
        this.statusCodeCounts = new HashMap<>();
    }

    public void update(int responseTime, int statusCode) {
        // Update min and max response times
        if (responseTime < minResponseTime || minResponseTime == 0) {
            minResponseTime = responseTime;
        }
        if (responseTime > maxResponseTime) {
            maxResponseTime = responseTime;
        }

        // Update average response time
        averageResponseTime = ((averageResponseTime * (statusCodeCounts.size() - 1)) + responseTime) / statusCodeCounts.size();

        // Update status code counts
        statusCodeCounts.put(Integer.toString(statusCode), statusCodeCounts.getOrDefault(Integer.toString(statusCode), 0) + 1);
    }

}
