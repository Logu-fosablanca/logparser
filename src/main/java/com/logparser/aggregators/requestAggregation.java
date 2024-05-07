package com.logparser.aggregators;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class requestAggregation {
    private ResponseTimes responseTimes;
    private StatusCodes statusCodes;

    public requestAggregation(ResponseTimes responseTimes, StatusCodes statusCodes) {
        this.responseTimes = responseTimes;
        this.statusCodes = statusCodes;
    }

}

class ResponseTimes {
    private int min;
    private int percentile50;
    private int percentile90;
    private int percentile95;
    private int percentile99;
    private int max;

    public ResponseTimes(int min, int percentile50, int percentile90, int percentile95, int percentile99, int max) {
        this.min = min;
        this.percentile50 = percentile50;
        this.percentile90 = percentile90;
        this.percentile95 = percentile95;
        this.percentile99 = percentile99;
        this.max = max;
    }
}

class StatusCodes {
    private int status2XX;
    private int status4XX;
    private int status5XX;

    public StatusCodes(int status2XX, int status4XX) {
        this.status2XX = status2XX;
        this.status4XX = status4XX;
    }
}