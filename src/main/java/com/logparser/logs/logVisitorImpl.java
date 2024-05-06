
package com.logparser.logs;

import com.logparser.aggregators.apmAggregation;
import com.logparser.aggregators.applicationAggregation;
import com.logparser.aggregators.requestAggregation;

import java.util.HashMap;
import java.util.Map;


public class logVisitorImpl implements logVisitor {
    // Map to store aggregations for APM logs
    private final Map<String, apmAggregation> apmAggregations = new HashMap<>();

    // Map to store aggregations for Application logs
    private final Map<String, applicationAggregation> applicationAggregations = new HashMap<>();

    // Map to store aggregations for Request logs
    private final Map<String, requestAggregation> requestAggregations = new HashMap<>();

    // Method to visit APM logs
    @Override
    public void visit(apmLog log) {
        /**
         * Need to Implement the min, max and avage System here
         */
    }

    // Method to visit Application logs
    @Override
    public void visit(applicationLog log) {
        /**
         * Need to implement the count here so if this is done
         */
    }

    // Method to visit Request logs
    @Override
    public void visit(requestLog log) {

        /***
         * Need to implement the percentile system in here
         */
//        // Implement logic to aggregate Request logs
//        // For example:
//        // If requestAggregations contains key with request URL, update the aggregation
//        // Otherwise, create a new aggregation and add it to the map
//        String url = log.getRequestUrl();
//        int responseTime = log.getResponseTimeMs();
//        int statusCode = log.getResponseStatusCode();
//
//        if (requestAggregations.containsKey(url)) {
//            requestAggregation aggregation = requestAggregations.get(url);
//            aggregation.update(responseTime, statusCode); // Implement update logic in RequestAggregation class
//        } else {
//            requestAggregation newAggregation = new requestAggregation();
//            newAggregation.update(responseTime, statusCode); // Implement update logic in RequestAggregation class
//            requestAggregations.put(url, newAggregation);
//        }
    }

    // Method to get aggregated data for APM logs
    public Map<String, apmAggregation> getApmAggregations() {
        return apmAggregations;
    }

    // Method to get aggregated data for Application logs
    public Map<String, applicationAggregation> getApplicationAggregations() {
        return applicationAggregations;
    }

    // Method to get aggregated data for Request logs
    public Map<String, requestAggregation> getRequestAggregations() {
        return requestAggregations;
    }
}
