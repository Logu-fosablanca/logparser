package com.logparser.aggregators;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
public class applicationAggregation implements processLog {
    List<Map<String,Integer>> levelCount;

    @Override
    public JsonObject  analysedLog(String fileName) {
        levelCount= new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName+".json"));
            String line;
            Gson gson = new Gson();

            while ((line = reader.readLine()) != null) {
                JsonObject jsonObject = gson.fromJson(line, JsonObject.class);
                String level = jsonObject.get("level").getAsString();
                updateLevelCount(levelCount, level);
            }
            System.out.print(levelCount);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertToOutput(levelCount);
    }

    public static JsonObject convertToOutput(List<Map<String, Integer>> metricCount) {
        JsonObject output = new JsonObject();
        for (Map<String, Integer> map : metricCount) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String level = entry.getKey();
                int count = entry.getValue();
                output.addProperty(level, count);
            }
        }
        return output;
    }

    private static void updateLevelCount(List<Map<String, Integer>> metricCount, String level) {
        boolean found = false;
        for (Map<String, Integer> map : metricCount) {
            if (map.containsKey(level)) {
                map.put(level, map.get(level) + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            Map<String, Integer> newEntry = new HashMap<>();
            newEntry.put(level, 1);
            metricCount.add(newEntry);
        }
    }
}
