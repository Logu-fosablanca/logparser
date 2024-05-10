package com.logparser.aggregators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class requestAggregation implements processLog {
    Map<String, List<Integer>> responseTimes;
    Map<String, Map<String, Integer>> responseStatusCodes;

    @Override
    public JsonObject analysedLog(String fileName) {
        responseTimes = new HashMap<>();
        responseStatusCodes = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName + ".json"));
            String line;
            Gson gson = new Gson();

            while ((line = reader.readLine()) != null) {
                JsonObject jsonObject = gson.fromJson(line, JsonObject.class);
                String requestUrl = jsonObject.get("requestUrl").getAsString();
                Integer responseTime = jsonObject.get("responseTimeMs").getAsInt();
                Integer statusCode = jsonObject.get("responseStatusCode").getAsInt();
                System.out.print(requestUrl);

                // Aggregate response times
                if (!responseTimes.containsKey(requestUrl)) {
                    responseTimes.put(requestUrl, new ArrayList<>());
                }
                responseTimes.get(requestUrl).add(responseTime);

                // Aggregate response status codes
                if (!responseStatusCodes.containsKey(requestUrl)) {
                    responseStatusCodes.put(requestUrl, new HashMap<>());
                }
                Map<String, Integer> statusCodes = responseStatusCodes.get(requestUrl);
                String statusCodeKey = getStatusCodeKey(statusCode);
                statusCodes.put(statusCodeKey, statusCodes.getOrDefault(statusCodeKey, 0) + 1);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertToOutput(responseTimes, responseStatusCodes);
    }

    private static String getStatusCodeKey(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) {
            return "2XX";
        } else if (statusCode >= 400 && statusCode < 500) {
            return "4XX";
        } else if (statusCode >= 500 && statusCode < 600) {
            return "5XX";
        } else {
            // You can handle other status code ranges as needed
            return "Other";
        }
    }


    public static JsonObject convertToOutput(Map<String, List<Integer>> responseTimes,
                                             Map<String, Map<String, Integer>> responseStatusCodes) {
        JsonObject output = new JsonObject();

        for (Map.Entry<String, List<Integer>> entry : responseTimes.entrySet()) {
            String requestUrl = entry.getKey();
            List<Integer> times = entry.getValue();
            JsonObject responseTimeObj = new JsonObject();
            int minTime = Collections.min(times);
            int maxTime = Collections.max(times);
            responseTimeObj.addProperty("min", minTime);
            responseTimeObj.addProperty("max", maxTime);

            // Calculate percentiles
            Collections.sort(times);
            responseTimeObj.addProperty("50_percentile", getPercentile(times, 50));
            responseTimeObj.addProperty("90_percentile", getPercentile(times, 90));
            responseTimeObj.addProperty("95_percentile", getPercentile(times, 95));
            responseTimeObj.addProperty("99_percentile", getPercentile(times, 99));

            // You can calculate other metrics like median, average, etc. as needed

            Map<String, Integer> statusCodes = responseStatusCodes.get(requestUrl);
            JsonObject statusCodesObj = new JsonObject();
            for (Map.Entry<String, Integer> statusCodeEntry : statusCodes.entrySet()) {
                statusCodesObj.addProperty(statusCodeEntry.getKey(), statusCodeEntry.getValue());
            }

            JsonObject urlObj = new JsonObject();
            urlObj.add("response_times", responseTimeObj);
            urlObj.add("status_codes", statusCodesObj);
            requestUrl=  requestUrl.replace("\"", "");

            output.add(requestUrl, urlObj);
        }

        return output;
    }

    private static double getPercentile(List<Integer> sortedData, double percentile) {
        if (percentile == 100.0) {
            return sortedData.get(sortedData.size() - 1);
        }
        int index = (int) Math.ceil((percentile / 100.0) * sortedData.size()) - 1;
        return sortedData.get(index);
    }
}
