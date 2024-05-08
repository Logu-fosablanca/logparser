package com.logparser;


import com.google.gson.JsonObject;
import com.logparser.aggregators.apmAggregation;
import com.logparser.aggregators.applicationAggregation;
import com.logparser.logs.apmLog;
import com.logparser.logs.applicationLog;
import com.logparser.logs.requestLog;
import com.logparser.parser.LogParser;
import com.logparser.visitors.logVisitor;
import com.logparser.visitors.logVisitorImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class App {
    public static void main(String[] args) {
        String filename="Inputs.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from the log file
            while ((line = br.readLine()) != null) {
                logVisitor visitor = new logVisitorImpl();
                String logType= classifyAndProcessLogEntry(line);
                if (logType=="APM"){
                    apmLog logEntry = new apmLog(line);
                    logEntry.accept(visitor);
                } else if (logType=="Application") {
                    applicationLog logEntry = new applicationLog(line);
                    logEntry.accept(visitor);
                } else if (logType=="Request") {
                    requestLog logEntry = new requestLog(line);
                    logEntry.accept(visitor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Parse the logs based on their type
         * for the apm aggregators just the count would be enought
         * for the request log aggregators the  would be the url will be mapped to the responsetimes in percentiles and status codes after the mapping
         * for the application log aggregators the count would be the meteric would be mapped with minimum , medium , max and avg values
         */
        applicationAggregation analysedApplicationLog = new applicationAggregation();
        JsonObject levelCount = analysedApplicationLog.analysedLog("application_log");
        System.out.print(levelCount);
        LogParser.writeOutputToFile(levelCount,"application");

        apmAggregation analysedApmLog= new apmAggregation();
        JsonObject metricStats = analysedApmLog.analysedLog("apm_log");
        System.out.print(metricStats);
        LogParser.writeOutputToFile(metricStats,"apm");


    }

    private static String classifyAndProcessLogEntry(String line) {
        return LogParser.parseLogLine(line);

    }



}

