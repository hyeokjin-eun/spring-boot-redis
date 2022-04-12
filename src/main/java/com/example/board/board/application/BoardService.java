package com.example.board.board.application;

import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;

public interface BoardService {

    BoardCreateResponseDto create(BoardCreateRequestDto boardCreateRequestDto);
}
