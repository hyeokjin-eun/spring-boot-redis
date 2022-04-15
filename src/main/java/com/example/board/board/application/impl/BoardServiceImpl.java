package com.example.board.board.application.impl;

import com.example.board.board.application.BoardService;
import com.example.board.board.dto.enums.BoardStatus;
import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import com.example.board.board.dto.response.BoardSelectResponseDto;
import com.example.board.board.infra.dao.BoardDao;
import com.example.board.board.infra.entity.Board;
import com.example.board.board.infra.dao.impl.BoardDaoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    @Override
    public BoardCreateResponseDto create(BoardCreateRequestDto boardCreateRequestDto) {
        Board board = boardDao.save(Board.builder()
                        .title(boardCreateRequestDto.getTitle())
                        .content(boardCreateRequestDto.getContent())
                        .status(BoardStatus.ACTIVE)
                        .build());

        return BoardCreateResponseDto.builder()
                .seq(board.getSeq())
                .title(board.getTitle())
                .content(board.getContent())
                .status(board.getStatus())
                .build();
    }

    @Override
    public BoardSelectResponseDto select(long seq) {
        Board board = boardDao.findById(seq);
        return BoardSelectResponseDto.builder()
                .seq(board.getSeq())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
