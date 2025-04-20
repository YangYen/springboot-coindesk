package com.example.coindesk.dto;

import com.example.coindesk.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;
    private Integer errorCode;
    
    @JsonIgnore
    private HttpStatus httpStatus;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .status("success")
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public static <T> BaseResponse<T> created(T data, String message) {
        return BaseResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public static BaseResponse<Void> deleted(String message) {
        return BaseResponse.<Void>builder()
                .status("success")
                .message(message)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return BaseResponse.<T>builder()
                .status("error")
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessageTemplate())
                .httpStatus(errorCode.getStatus())
                .build();
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message) {
        return BaseResponse.<T>builder()
                .status("error")
                .errorCode(errorCode.getCode())
                .message(message)
                .httpStatus(errorCode.getStatus())
                .build();
    }
} 