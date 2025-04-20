package com.example.coindesk.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.example.coindesk.dto.BaseResponse;
import com.example.coindesk.dto.CoindeskApiResponse;
import com.example.coindesk.dto.TransformedCoindeskApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoindeskApiCallingServiceTest {

    @Autowired
    private CoindeskApiService coindeskApiService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    void testGetCoindeskData() {
        System.out.println("\n=== 測試呼叫 Coindesk 原始 API ===");
        BaseResponse<CoindeskApiResponse> response = coindeskApiService.getCoindeskData();

        System.out.println("呼叫結果：");
        System.out.println("狀態：" + response.getStatus());
        System.out.println("\n原始資料：");
        try {
            // 美化輸出 JSON
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(response.getData());
            System.out.println(prettyJson);
        } catch (Exception e) {
            System.out.println("資料格式化失敗：" + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testGetTransformedData() {
        System.out.println("\n=== 測試呼叫資料轉換 API ===");
        BaseResponse<TransformedCoindeskApiResponse> response = coindeskApiService.getTransformedData();

        System.out.println("呼叫結果：");
        System.out.println("狀態：" + response.getStatus());
        System.out.println("\n轉換後資料：");
        try {
            // 美化輸出 JSON
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(response.getData());
            System.out.println(prettyJson);
        } catch (Exception e) {
            System.out.println("資料格式化失敗：" + e.getMessage());
        }
    }
}
