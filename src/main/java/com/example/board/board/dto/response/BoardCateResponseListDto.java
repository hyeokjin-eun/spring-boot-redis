package com.example.board.board.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardCateResponseListDto {
    private long seq;

    private String name;
}
