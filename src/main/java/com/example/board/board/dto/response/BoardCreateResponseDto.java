package com.example.board.board.dto.response;

import com.example.board.board.dto.enums.BoardStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardCreateResponseDto {
    private long seq;

    private String title;

    private String content;

    private BoardStatus status;
}

