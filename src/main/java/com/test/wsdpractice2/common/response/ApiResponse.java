package com.test.wsdpractice2.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse <T>{

    private Boolean isSuccess;
    private String status;
    private String message;
    private T payload;

    public static <T> ApiResponse<T> success(String code, String message, T payload){
        return new ApiResponse<>(true, code, message, payload);
    }

    public static <T> ApiResponse<T> fail(String code, String message, T detail){
        return new ApiResponse<>(false, code, message, detail);
    }
}
