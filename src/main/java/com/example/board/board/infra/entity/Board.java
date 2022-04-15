package com.example.board.board.infra.entity;

import com.example.board.board.dto.enums.BoardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private String title;

    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BoardStatus status;
}
