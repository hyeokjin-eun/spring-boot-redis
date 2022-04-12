package com.example.board.board.infra;

import com.example.board.board.infra.entity.Board;
import com.example.board.board.infra.repository.BoardRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Nested
@DisplayName("게시판 Repository")
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Nested
    @DisplayName("생성")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class SaveTest {
        private Board board;

        @BeforeEach
        public void setUp() {
            String TITLE = "title";
            String CONTENT = "content";
            board = Board.builder()
                    .title(TITLE)
                    .content(CONTENT)
                    .build();
        }

        @Test
        @DisplayName("Success")
        public void success() {
            boardRepository.save(board);
        }
    }
}
