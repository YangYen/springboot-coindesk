package com.example.coindesk.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coindesk.dto.BaseResponse;
import com.example.coindesk.dto.CreateCurrencyRequest;
import com.example.coindesk.dto.CurrencyResponse;
import com.example.coindesk.dto.UpdateCurrencyRequest;
import com.example.coindesk.entity.Currency;
import com.example.coindesk.exception.BaseException;
import com.example.coindesk.exception.ErrorCode;
import com.example.coindesk.repository.CurrencyRepository;

@Service
@Transactional
public class CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    @Transactional(readOnly = true)
    public BaseResponse<List<CurrencyResponse>> getAllCurrencies() {
        logger.info("Fetching all currencies");
        List<CurrencyResponse> currencyDetailResponses = currencyRepository.findAll().stream()
                .map(CurrencyResponse::fromEntity)
                .collect(Collectors.toList());
        logger.info("Found {} currencyDetailResponses", currencyDetailResponses.size());
        return BaseResponse.success(currencyDetailResponses);
    }

    @Transactional(readOnly = true)
    public BaseResponse<CurrencyResponse> getCurrencyById(Long id) {
        if (null == id) {
            logger.error("Currency id cannot be null");
            throw new BaseException(ErrorCode.INVALID_REQUEST_PARAMETER, "Currency id cannot be null");
        }

        logger.info("Fetching currency with id: {}", id);
        CurrencyResponse currencyDetailResponse = currencyRepository.findById(id)
                .map(CurrencyResponse::fromEntity)
                .orElseThrow(() -> {
                    logger.error("Currency not found with id: {}", id);
                    return new BaseException(ErrorCode.CURRENCY_NOT_FOUND, id);
                });
        logger.info("Found currencyDetailResponse: {}", currencyDetailResponse.getCode());
        return BaseResponse.success(currencyDetailResponse);
    }

    public BaseResponse<CurrencyResponse> createCurrency(CreateCurrencyRequest request) {
        if (null == request) {
            logger.error("Create currency request cannot be null");
            throw new BaseException(ErrorCode.INVALID_REQUEST_PARAMETER, "Create currency request cannot be null");
        }

        String code = request.getCode();
        String chineseName = request.getChineseName();

        logger.info("Creating new currency with code: {}", code);
        if (currencyRepository.existsByCode(code)) {
            logger.error("Failed to create currency due to duplicate code: {}", code);
            throw new BaseException(ErrorCode.DUPLICATE_CURRENCY_CODE, code);
        }

        Currency currency = new Currency();
        currency.setCode(code);
        currency.setChineseName(chineseName);

        Currency savedCurrency = currencyRepository.save(currency);
        logger.info("Successfully created currency with id: {}", savedCurrency.getId());
        return BaseResponse.created(
                CurrencyResponse.fromEntity(savedCurrency),
                "Currency created successfully");
    }

    public BaseResponse<CurrencyResponse> updateCurrency(Long id, UpdateCurrencyRequest request) {
        if (null == id) {
            logger.error("Currency id cannot be null");
            throw new BaseException(ErrorCode.INVALID_REQUEST_PARAMETER, "Currency id cannot be null");
        }

        if (null == request) {
            logger.error("Update currency request cannot be null");
            throw new BaseException(ErrorCode.INVALID_REQUEST_PARAMETER, "Update currency request cannot be null");
        }

        String code = request.getCode();
        String chineseName = request.getChineseName();

        logger.info("Updating currency with id: {}, new code: {}", id, code);
        Currency existingCurrency = currencyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Currency not found with id: {}", id);
                    return new BaseException(ErrorCode.CURRENCY_NOT_FOUND, id);
                });

        // 檢查新的代碼是否與其他貨幣衝突（排除自己）
        Currency duplicateCurrency = currencyRepository.findByCode(code);
        if (duplicateCurrency != null && !duplicateCurrency.getId().equals(id)) {
            logger.error("Failed to update currency due to duplicate code: {}", code);
            throw new BaseException(ErrorCode.DUPLICATE_CURRENCY_CODE, code);
        }

        existingCurrency.setCode(code);
        existingCurrency.setChineseName(chineseName);

        Currency updatedCurrency = currencyRepository.save(existingCurrency);
        logger.info("Successfully updated currency with id: {}", updatedCurrency.getId());
        return BaseResponse.success(
                CurrencyResponse.fromEntity(updatedCurrency),
                "Currency updated successfully");
    }

    public BaseResponse<Void> deleteCurrency(Long id) {
        if (null == id) {
            logger.error("Currency id cannot be null");
            throw new BaseException(ErrorCode.INVALID_REQUEST_PARAMETER, "Currency id cannot be null");
        }

        logger.info("Attempting to delete currency with id: {}", id);
        if (!currencyRepository.existsById(id)) {
            logger.error("Currency not found with id: {}", id);
            throw new BaseException(ErrorCode.CURRENCY_NOT_FOUND, id);
        }
        currencyRepository.deleteById(id);
        logger.info("Successfully deleted currency with id: {}", id);
        return BaseResponse.deleted("Currency deleted successfully");
    }
}