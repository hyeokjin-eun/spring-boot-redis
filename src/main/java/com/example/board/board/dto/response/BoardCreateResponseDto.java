package com.example.board.board.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardCreateResponseDto {
    private String title;

    private String content;
}

