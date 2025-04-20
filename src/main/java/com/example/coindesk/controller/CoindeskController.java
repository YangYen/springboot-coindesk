package com.example.coindesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coindesk.dto.BaseResponse;
import com.example.coindesk.dto.CoindeskApiResponse;
import com.example.coindesk.dto.TransformedCoindeskApiResponse;
import com.example.coindesk.service.CoindeskApiService;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {

    @Autowired
    private CoindeskApiService coindeskApiService;

    @GetMapping("/original")
    public BaseResponse<CoindeskApiResponse> getOriginalData() {
        return coindeskApiService.getCoindeskData();
    }

    @GetMapping("/transformed")
    public BaseResponse<TransformedCoindeskApiResponse> getTransformedData() {
        return coindeskApiService.getTransformedData();
    }
}