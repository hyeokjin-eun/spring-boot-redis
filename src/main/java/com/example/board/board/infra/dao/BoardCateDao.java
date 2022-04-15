package com.example.board.board.infra.dao;

import com.example.board.board.infra.entity.BoardCate;

import java.util.List;

public interface BoardCateDao {
    List<BoardCate> findAll();
}
