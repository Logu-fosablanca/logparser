package com.logparser.aggregators;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class apmAggregation {
    private double min;
    private double max;
    private double average;
    private double median;


    public void update(double value) {

        min = Math.min(min, value);
        max = Math.max(max, value);



    }
}
