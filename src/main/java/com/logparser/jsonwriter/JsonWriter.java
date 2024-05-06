package com.logparser.jsonwriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class JsonWriter {
    public static void writeToJsonFile(String filename, Map<String, Object> data) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer);
        }
    }
}
