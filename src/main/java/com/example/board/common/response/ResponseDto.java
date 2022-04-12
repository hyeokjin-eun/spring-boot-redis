package com.example.board.common.response;

import com.example.board.common.enums.ResponseCode;
import lombok.*;
import org.springframework.http.ResponseEntity;

@Data
@ToString
@Builder
public class ResponseDto<T> {
    private String code;

    private String message;

    private T data;

    private ResponseDto (String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private static <T> ResponseDto<T> create(ResponseCode responseCode) {
        return ResponseDto.<T>builder()
                .code(responseCode.getCode())
                .message(responseCode.name())
                .build();
    }

    public static <T> ResponseEntity<ResponseDto<T>> success() {
        return ResponseEntity.ok()
                .body(ResponseDto.create(ResponseCode.SUCCESS));
    }

    public static <T> ResponseEntity<ResponseDto<T>> success(T data) {
        ResponseDto<T> responseDto = create(ResponseCode.SUCCESS);
        responseDto.setData(data);
        return ResponseEntity.ok()
                .body(responseDto);
    }
}
