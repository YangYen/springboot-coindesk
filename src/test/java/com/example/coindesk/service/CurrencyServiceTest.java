package com.example.coindesk.service;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.coindesk.dto.BaseResponse;
import com.example.coindesk.dto.CreateCurrencyRequest;
import com.example.coindesk.dto.CurrencyResponse;
import com.example.coindesk.dto.UpdateCurrencyRequest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @Test
    @Order(1)
    void testGetAllCurrencies() {
        System.out.println("\n=== 測試查詢所有幣別 API ===");
        BaseResponse<List<CurrencyResponse>> response = currencyService.getAllCurrencies();

        System.out.println("查詢結果：");
        System.out.println("狀態：" + response.getStatus());
        System.out.println("幣別列表：");
        response.getData().forEach(currency -> {
            System.out.println("- 代碼：" + currency.getCode() + ", 中文名稱：" + currency.getChineseName());
        });
    }

    @Test
    @Order(2)
    void testCreateCurrency() {
        System.out.println("\n=== 測試新增幣別 API ===");
        CreateCurrencyRequest request = new CreateCurrencyRequest();
        request.setCode("TWD");
        request.setChineseName("台幣");

        BaseResponse<CurrencyResponse> response = currencyService.createCurrency(request);

        System.out.println("新增結果：");
        System.out.println("狀態：" + response.getStatus());
        System.out.println("訊息：" + response.getMessage());
        System.out.println("幣別資料：");
        System.out.println("- ID：" + response.getData().getId());
        System.out.println("- 代碼：" + response.getData().getCode());
        System.out.println("- 中文名稱：" + response.getData().getChineseName());

    }

    @Test
    @Order(3)
    void testGetCurrencyById() {
        System.out.println("\n=== 測試查詢單一幣別 API ===");
        BaseResponse<CurrencyResponse> response = currencyService.getCurrencyById(4l);

        System.out.println("查詢結果：");
        System.out.println("狀態：" + response.getStatus());
        System.out.println("幣別資料：");
        System.out.println("- ID：" + response.getData().getId());
        System.out.println("- 代碼：" + response.getData().getCode());
        System.out.println("- 中文名稱：" + response.getData().getChineseName());
    }

    @Test
    @Order(4)
    void testUpdateCurrency() {
        System.out.println("\n=== 測試更新幣別 API ===");
        UpdateCurrencyRequest request = new UpdateCurrencyRequest();
        request.setCode("HKD");
        request.setChineseName("港幣");

        BaseResponse<CurrencyResponse> response = currencyService.updateCurrency(1l, request);

        System.out.println("更新結果：");
        System.out.println("狀態：" + response.getStatus());
        System.out.println("訊息：" + response.getMessage());
        System.out.println("更新後資料：");
        System.out.println("- ID：" + response.getData().getId());
        System.out.println("- 代碼：" + response.getData().getCode());
        System.out.println("- 中文名稱：" + response.getData().getChineseName());
    }

    @Test
    @Order(5)
    void testDeleteCurrency() {
        System.out.println("\n=== 測試刪除幣別 API ===");
        BaseResponse<Void> response = currencyService.deleteCurrency(1l);

        System.out.println("刪除結果：");
        System.out.println("狀態：" + response.getStatus());
        System.out.println("訊息：" + response.getMessage());

        // 確認刪除後的列表
        System.out.println("\n=== 確認刪除後的幣別列表 ===");
        BaseResponse<List<CurrencyResponse>> finalResponse = currencyService.getAllCurrencies();
        System.out.println("最終列表：");
        finalResponse.getData().forEach(currency -> {
            System.out.println("- 代碼：" + currency.getCode() + ", 中文名稱：" + currency.getChineseName());
        });
    }
}
