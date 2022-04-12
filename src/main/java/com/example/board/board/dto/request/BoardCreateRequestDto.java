package com.example.board.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@ToString
public class BoardCreateRequestDto {

    @NotEmpty
    @Size(max = 50)
    private String title;

    @NotEmpty
    private String content;
}
