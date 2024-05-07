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

}
