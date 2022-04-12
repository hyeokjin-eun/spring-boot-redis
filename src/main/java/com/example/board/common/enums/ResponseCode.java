package com.example.board.common.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("0000"),
    FAIL("9999");

    private String code;

    ResponseCode(String code) {
        this.code = code;
    }
}
