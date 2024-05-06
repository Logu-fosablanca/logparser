package com.logparser.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogParser {
    public LogEntry parseLogLine(String line) {
        Map<String, String> keyValuePairs = new HashMap<>();
        String[] parts = line.trim().split("\\s+");

        for (String part : parts) {
            String[] keyValue = part.split("=", 2);
            if (keyValue.length == 2) {
                keyValuePairs.put(keyValue[0], keyValue[1]);
            }
        }
        // Extract fields from keyValuePairs map
        String timestamp = keyValuePairs.get("timestamp");
        String type = extractLogType(keyValuePairs);
        String level = keyValuePairs.getOrDefault("level", "");
        String message = keyValuePairs.getOrDefault("message", "");
        String requestMethod = keyValuePairs.getOrDefault("request_method", "");
        System.out.print(message);
        // Create and return a LogEntry object
        return new LogEntry(timestamp, type, level, message, requestMethod);
    }

    private String extractLogType(Map<String, String> keyValuePairs) {

        if (keyValuePairs.containsKey("metric")) {
            return "APM";
        } else if (keyValuePairs.containsKey("level")) {
            return "Application";
        } else if (keyValuePairs.containsKey("request_method")) {
            return "Request";
        } else {
            // Handle unrecognized log type
            return "Unknown";
        }
    }

    public List<LogEntry> parseLogFile(String filename) {
        List<LogEntry> logEntries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry logEntry = parseLogLine(line);
                logEntries.add(logEntry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return logEntries;
    }
}
