package com.example.security.constant;

public enum ResponseMessageEnum {
    SUCCESS("response success"),
    ERROR("response error");
    private String message;

    ResponseMessageEnum() {
    }
    ResponseMessageEnum(String message) {
        this.message = message;
    }
}
