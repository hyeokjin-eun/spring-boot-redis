package com.example.board.board.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardCateResponseDto {
    private List<BoardCateResponseListDto> list;
}
