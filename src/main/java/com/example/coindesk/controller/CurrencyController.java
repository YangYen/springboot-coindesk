package com.example.coindesk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coindesk.dto.BaseResponse;
import com.example.coindesk.dto.CreateCurrencyRequest;
import com.example.coindesk.dto.CurrencyResponse;
import com.example.coindesk.dto.UpdateCurrencyRequest;
import com.example.coindesk.service.CurrencyService;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public BaseResponse<List<CurrencyResponse>> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/{id}")
    public BaseResponse<CurrencyResponse> getCurrencyById(@PathVariable Long id) {
        return currencyService.getCurrencyById(id);
    }

    @PostMapping
    public BaseResponse<CurrencyResponse> createCurrency(@Valid @RequestBody CreateCurrencyRequest request) {
        return currencyService.createCurrency(request);
    }

    @PutMapping("/{id}")
    public BaseResponse<CurrencyResponse> updateCurrency(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCurrencyRequest request) {
        return currencyService.updateCurrency(id, request);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteCurrency(@PathVariable Long id) {
        return currencyService.deleteCurrency(id);
    }
}