package com.example.board.board.application.impl;

import com.example.board.board.application.BoardService;
import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import com.example.board.board.dto.response.BoardSelectResponseDto;
import com.example.board.board.infra.entity.Board;
import com.example.board.board.infra.repository.BoardRepository;
import com.example.board.common.error.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardCreateResponseDto create(BoardCreateRequestDto boardCreateRequestDto) {
        Board board = boardRepository.save(Board.builder()
                        .title(boardCreateRequestDto.getTitle())
                        .content(boardCreateRequestDto.getContent())
                        .build());

        return BoardCreateResponseDto.builder()
                .seq(board.getSeq())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }

    @Override
    public BoardSelectResponseDto select(long seq) {
        Board board = boardRepository.findById(seq)
                .orElseThrow(BoardNotFoundException::new);

        return BoardSelectResponseDto.builder()
                .seq(board.getSeq())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
