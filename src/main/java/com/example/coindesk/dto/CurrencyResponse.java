package com.example.coindesk.dto;

import com.example.coindesk.entity.Currency;
import lombok.Data;

@Data
public class CurrencyResponse {
    private Long id;
    private String code;
    private String chineseName;

    public static CurrencyResponse fromEntity(Currency currency) {
        CurrencyResponse response = new CurrencyResponse();
        response.setId(currency.getId());
        response.setCode(currency.getCode());
        response.setChineseName(currency.getChineseName());
        return response;
    }
} 