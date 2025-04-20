package com.example.coindesk.exception;

import com.example.coindesk.dto.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Void>> handleBaseException(BaseException ex) {
        logger.error("Handling BaseException: {}", ex.getMessage());
        BaseResponse<Void> response = BaseResponse.error(ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        logger.error("Validation error: {}", errorMessage);
        BaseResponse<Void> response = BaseResponse.error(ErrorCode.VALIDATION_ERROR, errorMessage);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception ex) {
        logger.error("Handling unexpected Exception: ", ex);
        BaseResponse<Void> response = BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}