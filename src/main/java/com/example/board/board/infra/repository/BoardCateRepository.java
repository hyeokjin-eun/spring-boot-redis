package com.example.board.board.infra.repository;

import com.example.board.board.infra.entity.BoardCate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCateRepository extends JpaRepository<BoardCate, Long> {
}
