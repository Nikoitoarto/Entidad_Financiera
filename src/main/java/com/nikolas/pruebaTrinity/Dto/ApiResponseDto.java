package com.nikolas.pruebaTrinity.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto<T> {
    private T data;
    private String message;
    private Boolean status;

    public ApiResponseDto(T data, String message, Boolean status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
