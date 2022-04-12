package com.example.board.common.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("0000");

    private String code;

    ResponseCode(String code) {
        this.code = code;
    }
}
