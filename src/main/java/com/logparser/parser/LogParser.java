package com.logparser.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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

    public static void writeOutputToFile(JsonObject output, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName+".json"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(output, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteLogFile(fileName+"_log.json");
    }

    private static void deleteLogFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully: " + fileName);
            } else {
                System.out.println("Failed to delete the file: " + fileName);
            }
        } else {
            System.out.println("File does not exist: " + fileName);
        }
    }

}
