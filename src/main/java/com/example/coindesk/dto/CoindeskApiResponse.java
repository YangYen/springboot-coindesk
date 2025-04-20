package com.example.coindesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
public class CoindeskApiResponse {
    private Time time;
    private String disclaimer;
    private String chartName;

    @JsonProperty("bpi")
    private Map<String, CurrencyInfo> currencyInfoMap;

    @Data
    public static class Time {
        private String updated;
        private String updatedISO;
        private String updateduk;
    }

    @Data
    public static class CurrencyInfo {
        private String code;
        private String symbol;
        private String rate;
        private String description;

        @JsonProperty("rate_float")
        private Double rateFloat;
    }
}