package com.example.board.board.infra.dao.impl;

import com.example.board.board.infra.dao.BoardCateDao;
import com.example.board.board.infra.entity.BoardCate;
import com.example.board.board.infra.repository.BoardCateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardCateDaoImpl implements BoardCateDao {

    private final BoardCateRepository boardCateRepository;

    @Override
    @Cacheable(value = "boardCate", cacheManager = "ehCacheManager")
    public List<BoardCate> findAll() {
        return boardCateRepository.findAll();
    }
}
