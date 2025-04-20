package com.example.coindesk.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.coindesk.dto.BaseResponse;
import com.example.coindesk.dto.CoindeskApiResponse;
import com.example.coindesk.dto.TransformedCoindeskApiResponse;
import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;

@Service
public class CoindeskApiService {
    private static final Logger logger = LoggerFactory.getLogger(CoindeskApiService.class);

    @Value("${coindesk.api.url}")
    private String coindeskApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyRepository currencyRepository;

    private CoindeskApiResponse getOriginalCoindeskData() {
        return restTemplate.getForObject(coindeskApiUrl, CoindeskApiResponse.class);
    }

    public BaseResponse<CoindeskApiResponse> getCoindeskData() {
        try {
            return BaseResponse.success(getOriginalCoindeskData());
        } catch (Exception e) {
            logger.error("撈取原始資料失敗: {}", e.getMessage());
            throw e;
        }
    }

    public BaseResponse<TransformedCoindeskApiResponse> getTransformedData() {
        try {
            CoindeskApiResponse coindeskData = getOriginalCoindeskData();
            TransformedCoindeskApiResponse response = new TransformedCoindeskApiResponse();

            // 轉換時間格式
            response.setUpdateTime(transformDateTime(coindeskData.getTime().getUpdatedISO()));

            // 轉換幣別資訊
            List<TransformedCoindeskApiResponse.CurrencyInfo> currencyInfoList = new ArrayList<>();
            coindeskData.getCurrencyInfoMap().forEach((code, apiInfo) -> {
                Currency dbCurrency = currencyRepository.findByCode(code);
                if (null != dbCurrency) {
                    TransformedCoindeskApiResponse.CurrencyInfo currencyInfo = new TransformedCoindeskApiResponse.CurrencyInfo();
                    // 設置來自資料庫的資料
                    currencyInfo.setCode(code);
                    currencyInfo.setChineseName(dbCurrency.getChineseName());

                    // 設置來自 API 的資料
                    currencyInfo.setRate(apiInfo.getRateFloat());

                    currencyInfoList.add(currencyInfo);
                }
            });

            response.setCurrencies(currencyInfoList);
            return BaseResponse.success(response);
        } catch (Exception e) {
            logger.error("幣別資料轉換失敗: {}", e.getMessage());
            throw e;
        }
    }

    private String transformDateTime(String isoDateTime) {
        try {
            // 使用 ISO 8601 格式解析，包含時區偏移
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date date = isoFormat.parse(isoDateTime);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return outputFormat.format(date);
        } catch (ParseException e) {
            logger.error("時間格式轉換失敗: {}", e.getMessage());
            throw new RuntimeException("時間格式轉換失敗: " + e.getMessage());
        }
    }
}