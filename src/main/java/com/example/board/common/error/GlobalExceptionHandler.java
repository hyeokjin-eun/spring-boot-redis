package com.example.board.common.error;

import com.example.board.common.enums.ResponseCode;
import com.example.board.common.error.exception.BusinessException;
import com.example.board.common.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto<String>> handleRuntimeException(BusinessException e) {
        log.error(e.getMessage());
        return ResponseDto.fail(HttpStatus.NOT_FOUND, ResponseCode.FAIL);
    }
}
