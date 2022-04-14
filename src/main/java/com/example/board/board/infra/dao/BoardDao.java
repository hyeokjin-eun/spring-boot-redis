package com.example.board.board.infra.dao;

import com.example.board.board.infra.entity.Board;

public interface BoardDao {

    Board save(Board board);

    Board findById(long seq);
}
