package com.logparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.logparser.aggregators.apmAggregation;
import com.logparser.aggregators.applicationAggregation;
// import com.logparser.aggregators.requestAggregation; // Import request aggregation
import com.logparser.logs.apmLog;
import com.logparser.logs.applicationLog;
import com.logparser.logs.requestLog;
import com.logparser.parser.LogParser;
import com.logparser.visitors.logVisitor;
import com.logparser.visitors.logVisitorImpl;

public class App {
    public static void main(String[] args) {
        String filename = "Inputs.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from the log file
            while ((line = br.readLine()) != null) {
                logVisitor visitor = new logVisitorImpl();
                String logType = classifyAndProcessLogEntry(line);
                if (logType.equals("APM")) { // Use equals() for string comparison
                    apmLog logEntry = new apmLog(line);
                    logEntry.accept(visitor);
                } else if (logType.equals("Application")) { // Use equals() for string comparison
                    applicationLog logEntry = new applicationLog(line);
                    logEntry.accept(visitor);
                } else if (logType.equals("Request")) { // Use equals() for string comparison
                    requestLog logEntry = new requestLog(line);
                    logEntry.accept(visitor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Application Aggregation
        applicationAggregation analysedApplicationLog = new applicationAggregation();
        JsonObject applicationStats = analysedApplicationLog.analysedLog("application_log");
        LogParser.writeOutputToFile(applicationStats, "application");

        // APM Aggregation
        apmAggregation analysedApmLog = new apmAggregation();
        JsonObject apmStats = analysedApmLog.analysedLog("apm_log");
        LogParser.writeOutputToFile(apmStats, "apm");

        // Request Aggregation
        // requestAggregation analysedRequestLog = new requestAggregation();
        // JsonObject requestStats = analysedRequestLog.analysedLog("request_log");
        // LogParser.writeOutputToFile(requestStats, "request");
    }

    private static String classifyAndProcessLogEntry(String line) {
        return LogParser.parseLogLine(line);
    }
}
