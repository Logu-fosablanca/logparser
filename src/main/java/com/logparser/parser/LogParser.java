package com.logparser.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogParser {
    public static String parseLogLine(String line) {
        Map<String, String> keyValuePairs = new HashMap<>();
        String[] parts = line.split("\\s+");

        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                keyValuePairs.put(keyValue[0], keyValue[1]);
            }
        }

        return extractLogType(keyValuePairs);

    }

    private static String extractLogType(Map<String, String> keyValuePairs) {

        if (keyValuePairs.containsKey("metric")) {
            return "APM";
        } else if (keyValuePairs.containsKey("level")) {
            return "Application";
        } else if (keyValuePairs.containsKey("request_method")) {
            return "Request";
        } else {
            return "Unknown";
        }
    }

    public static void dumpLogToFile(Object log, String dumpfile) {



    }

}
