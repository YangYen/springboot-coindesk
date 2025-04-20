package com.example.coindesk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.example.coindesk.dto.BaseResponse;
import com.example.coindesk.dto.CoindeskApiResponse;
import com.example.coindesk.dto.TransformedCoindeskApiResponse;
import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;

@ExtendWith(MockitoExtension.class)
public class CoindeskApiServiceTest {

    private static final String TEST_API_URL = "http://test.api.url";

    @InjectMocks
    private CoindeskApiService coindeskApiService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(coindeskApiService, "coindeskApiUrl", TEST_API_URL);
    }

    @Test
    void testGetTransformedData() {
        // 準備模擬資料
        CoindeskApiResponse mockResponse = new CoindeskApiResponse();
        CoindeskApiResponse.Time time = new CoindeskApiResponse.Time();
        time.setUpdatedISO("2024-01-01T12:00:00+00:00");
        mockResponse.setTime(time);

        Map<String, CoindeskApiResponse.CurrencyInfo> currencyMap = new HashMap<>();
        CoindeskApiResponse.CurrencyInfo usdInfo = new CoindeskApiResponse.CurrencyInfo();
        usdInfo.setRateFloat(45000.0);
        currencyMap.put("USD", usdInfo);
        mockResponse.setCurrencyInfoMap(currencyMap);

        Currency mockCurrency = new Currency();
        mockCurrency.setCode("USD");
        mockCurrency.setChineseName("美元");

        // 設置 Mock 行為
        when(restTemplate.getForObject(eq(TEST_API_URL), eq(CoindeskApiResponse.class)))
                .thenReturn(mockResponse);
        when(currencyRepository.findByCode("USD"))
                .thenReturn(mockCurrency);

        // 執行測試
        BaseResponse<TransformedCoindeskApiResponse> result = coindeskApiService.getTransformedData();

        // 驗證結果
        assertNotNull(result);
        assertEquals("success", result.getStatus());
        assertNotNull(result.getData());

        TransformedCoindeskApiResponse transformedData = result.getData();
        assertEquals("2024/01/01 12:00:00", transformedData.getUpdateTime());

        List<TransformedCoindeskApiResponse.CurrencyInfo> currencies = transformedData.getCurrencies();
        assertNotNull(currencies);
        assertEquals(1, currencies.size());

        TransformedCoindeskApiResponse.CurrencyInfo currency = currencies.get(0);
        assertEquals("USD", currency.getCode());
        assertEquals("美元", currency.getChineseName());
        assertEquals(45000.0, currency.getRate());

        // 驗證 Mock 是否被調用
        verify(restTemplate).getForObject(eq(TEST_API_URL), eq(CoindeskApiResponse.class));
        verify(currencyRepository).findByCode("USD");
    }
}
