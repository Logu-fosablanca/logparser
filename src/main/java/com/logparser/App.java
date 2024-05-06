package com.logparser;

import com.logparser.jsonwriter.JsonWriter;
import com.logparser.parser.LogEntry;
import com.logparser.parser.LogParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        String filename ;
//        if (args[0]==null){
            filename="Inputs.txt";
//        }
        try {
            LogParser logParser = new LogParser();
            List<LogEntry> logEntries = logParser.parseLogFile(filename);

            System.out.println(logEntries.toString());

            List<LogEntry> apmLogs = filterLogsByType(logEntries, "APM");
            LogAggregatorImpl apmAggregator = new LogAggregatorImpl(apmLogs);
            apmAggregator.aggregate(apmLogs);
            Map<String, Object> apmData = apmAggregator.toJson();
            JsonWriter.writeToJsonFile("apm.json", apmData);

            List<LogEntry> applicationLogs = filterLogsByType(logEntries, "Application");
            LogAggregatorImpl applicationAggregator = new LogAggregatorImpl(applicationLogs);
            applicationAggregator.aggregate(applicationLogs);
            Map<String, Object> applicationData = applicationAggregator.toJson();
            JsonWriter.writeToJsonFile("application.json", applicationData);

            List<LogEntry> requestLogs = filterLogsByType(logEntries, "Request");
            LogAggregatorImpl requestAggregator = new LogAggregatorImpl(requestLogs);
            requestAggregator.aggregate(requestLogs);
            Map<String, Object> requestData = requestAggregator.toJson();
            JsonWriter.writeToJsonFile("request.json", requestData);

            System.out.println("JSON files created successfully.");
        } catch (IOException e) {
            System.err.println("Error reading or writing log file: " + e.getMessage());
        }
    }

    static List<LogEntry> filterLogsByType(List<LogEntry> logEntries, String type) {
        List<LogEntry> filteredLogs = new ArrayList<>();
        for (LogEntry log : logEntries) {
            if (log.getType().equals(type)) {
                filteredLogs.add(log);
            }
        }
        return filteredLogs;
    }
}

