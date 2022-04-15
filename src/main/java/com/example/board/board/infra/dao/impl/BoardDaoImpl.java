package com.example.board.board.infra.dao.impl;

import com.example.board.board.infra.dao.BoardDao;
import com.example.board.board.infra.entity.Board;
import com.example.board.board.infra.repository.BoardRepository;
import com.example.board.board.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao {

    private final BoardRepository boardRepository;

    @CachePut(value = "board", key = "#board.seq", cacheManager = "redisCacheManager")
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Cacheable(value = "board", key = "#seq", cacheManager = "redisCacheManager")
    public Board findById(long seq) {
        return boardRepository.findById(seq)
                .orElseThrow(BoardNotFoundException::new);
    }
}
