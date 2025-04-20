package com.example.coindesk.dto;

import java.util.List;

import lombok.Data;

/**
 * Response DTO for currency information combining data from database and
 * Coindesk API
 */
@Data
public class TransformedCoindeskApiResponse {
    private String updateTime; // Last update time in yyyy/MM/dd HH:mm:ss format
    private List<CurrencyInfo> currencies;

    @Data
    public static class CurrencyInfo {
        private String code; // Currency code (from database)
        private String chineseName; // Chinese name (from database)
        private double rate; // Exchange rate (from Coindesk API)
    }
}