package com.logparser;


import com.logparser.logs.apmLog;
import com.logparser.logs.applicationLog;
import com.logparser.logs.requestLog;
import com.logparser.parser.LogParser;
import com.logparser.visitors.logVisitor;
import com.logparser.visitors.logVisitorImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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
                    visitor.visit(logEntry);
                } else if (logType=="Application") {
                    applicationLog logEntry = new applicationLog(line);
                    visitor.visit(logEntry);
                } else if (logType=="Request") {
                    requestLog logEntry = new requestLog(line);
                    visitor.visit(logEntry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String classifyAndProcessLogEntry(String line) {
        String logType= LogParser.parseLogLine(line);
        return logType;


    }

}

