package com.example.board.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class BoardSelectResponseDto {
    private long seq;

    private String title;

    private String content;
}
