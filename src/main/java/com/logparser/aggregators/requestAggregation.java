package com.logparser.aggregators;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class requestAggregation implements processLog{

    @Override
    public Object analysedLog(String filename) {
        return null;
    }
}

