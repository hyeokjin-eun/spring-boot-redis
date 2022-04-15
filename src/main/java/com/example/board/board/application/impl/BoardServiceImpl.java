package com.example.board.board.application.impl;

import com.example.board.board.application.BoardService;
import com.example.board.board.dto.enums.BoardStatus;
import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCateResponseDto;
import com.example.board.board.dto.response.BoardCateResponseListDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import com.example.board.board.dto.response.BoardSelectResponseDto;
import com.example.board.board.exception.BoardCateNotFoundException;
import com.example.board.board.infra.dao.BoardCateDao;
import com.example.board.board.infra.dao.BoardDao;
import com.example.board.board.infra.entity.Board;
import com.example.board.board.infra.entity.BoardCate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    private final BoardCateDao boardCateDao;

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

    @Override
    public BoardCateResponseDto cates() {
        List<BoardCateResponseListDto> boardCateResponseListDtoList = boardCateDao.findAll().stream()
                .map(boardCate -> BoardCateResponseListDto.builder()
                        .seq(boardCate.getSeq())
                        .name(boardCate.getName())
                        .build())
                .collect(Collectors.toList());

        if (boardCateResponseListDtoList.isEmpty()) {
            throw new BoardCateNotFoundException();
        }

        return BoardCateResponseDto.builder()
                .list(boardCateResponseListDtoList)
                .build();
    }
}
