package com.logparser.aggregators;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class applicationAggregation {
    private int errorCount;
    private int warningCount;
    private int infoCount;
    private int debugCount;
}
