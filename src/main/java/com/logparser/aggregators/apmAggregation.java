package com.logparser.aggregators;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Data
@Getter
@Setter
public class apmAggregation implements processLog {

    List<Map<String, JsonArray>> metricAnalysis;

    @Override
    public JsonObject analysedLog(String filename) {
       metricAnalysis = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filename+".json"));
            String line;
            Gson gson = new Gson();

            while ((line = reader.readLine()) != null) {
                JsonObject jsonObject = gson.fromJson(line, JsonObject.class);
                 String metric = jsonObject.get("metric").getAsString();
                 Integer value= jsonObject.get("value").getAsInt();

                 if(metricAnalysis.isEmpty()){
                     Map<String, JsonArray> log = new HashMap<>();
                     JsonArray newMetricValues = new JsonArray();
                     newMetricValues.add(value);
                     log.put(metric, newMetricValues);
                     metricAnalysis.add(log);
                     System.out.print(value);

                 } else {
                     updateMetricLogs(metricAnalysis, metric, value);
                 }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calculateStatistics(metricAnalysis);
    }

    public static void updateMetricLogs(List<Map<String, JsonArray>> metricAnalysis, String metric, Integer value) {

        for (Map<String, JsonArray> log : metricAnalysis) {
            if (log.containsKey(metric)) {
                JsonArray metricValues = log.get(metric);
                metricValues.add(value);
            } else {
                JsonArray newMetricValues = new JsonArray();
                newMetricValues.add(value);
                log.put(metric, newMetricValues);
            }
        }
        System.out.print(metricAnalysis);
    }


    private static JsonObject calculateStatistics(List<Map<String, JsonArray>> metricAnalysis) {
        JsonObject metrics = new JsonObject();

        for (Map<String, JsonArray> log : metricAnalysis) {
            for (Map.Entry<String, JsonArray> entry : log.entrySet()) {
                String metricName = entry.getKey();
                JsonArray values = entry.getValue();

                JsonObject metricStats = new JsonObject();

                // Calculate minimum
                int min = Integer.MAX_VALUE;
                for (int i = 0; i < values.size(); i++) {
                    int val = values.get(i).getAsInt();
                    if (val < min) {
                        min = val;
                    }
                }
                metricStats.addProperty("minimum", min);

                // Sort values for calculating median
                List<Integer> sortedValues = new ArrayList<>();
                for (int i = 0; i < values.size(); i++) {
                    sortedValues.add(values.get(i).getAsInt());
                }
                Collections.sort(sortedValues);

                // Calculate median
                double median;
                if (sortedValues.size() % 2 == 0) {
                    median = (sortedValues.get(sortedValues.size() / 2 - 1) + sortedValues.get(sortedValues.size() / 2)) / 2.0;
                } else {
                    median = sortedValues.get(sortedValues.size() / 2);
                }
                metricStats.addProperty("median", median);

                // Calculate average
                double sum = 0;
                for (int i = 0; i < values.size(); i++) {
                    sum += values.get(i).getAsInt();
                }
                double average = sum / values.size();
                metricStats.addProperty("average", average);

                // Calculate maximum
                int max = Integer.MIN_VALUE;
                for (int i = 0; i < values.size(); i++) {
                    int val = values.get(i).getAsInt();
                    if (val > max) {
                        max = val;
                    }
                }
                metricStats.addProperty("maximum", max);

                metrics.add(metricName, metricStats);
            }
        }

        return metrics;
    }
}
