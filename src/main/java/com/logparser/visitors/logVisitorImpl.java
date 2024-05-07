
package com.logparser.visitors;

import com.logparser.aggregators.apmAggregation;
import com.logparser.aggregators.applicationAggregation;
import com.logparser.aggregators.requestAggregation;
import com.logparser.logs.apmLog;
import com.logparser.logs.applicationLog;
import com.logparser.logs.requestLog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class logVisitorImpl implements logVisitor {

    private static final String APM_LOG_FILE = "apm_log.json";
    private static final String APPLICATION_LOG_FILE = "application_log.json";
    private static final String REQUEST_LOG_FILE = "request_log.json";

    @Override
    public void visit(apmLog logEntry) {
        /**
         * Need to Implement the min, max and avage System here
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APM_LOG_FILE, true))) {
            writer.write(logEntry.toJson());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to visit Application logs
    @Override
    public void visit(applicationLog logEntry) {
        /**
         * Need to implement the count here so if this is done
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPLICATION_LOG_FILE, true))) {
            writer.write(logEntry.toJson());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Method to visit Request logs
    @Override
    public void visit(requestLog logEntry) {

        /***
         * Need to implement the percentile system in here
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REQUEST_LOG_FILE, true))) {
            writer.write(logEntry.toJson());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
