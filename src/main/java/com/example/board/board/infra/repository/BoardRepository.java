package com.example.board.board.infra.repository;

import com.example.board.board.infra.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
