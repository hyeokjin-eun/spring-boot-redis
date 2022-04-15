package com.example.board.board.application;

import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCateResponseDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import com.example.board.board.dto.response.BoardSelectResponseDto;

public interface BoardService {

    BoardCreateResponseDto create(BoardCreateRequestDto boardCreateRequestDto);

    BoardSelectResponseDto select(long seq);

    BoardCateResponseDto cates();
}
