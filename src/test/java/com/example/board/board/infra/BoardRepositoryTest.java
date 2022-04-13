package com.example.board.board.infra;

import com.example.board.board.infra.entity.Board;
import com.example.board.board.infra.repository.BoardRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@Nested
@DisplayName("게시판 Repository")
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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
        @Order(0)
        public void success() {
            testEntityManager.persist(board);
        }
    }

    @Nested
    @DisplayName("검색")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class FindByTest {

        @BeforeEach
        public void setUp() {
            for (int i = 0; i < 10; i++) {
                testEntityManager.persist(Board.builder()
                        .title("title" + (i + 1))
                        .content("content" + (i + 1))
                        .build());
            }
        }

        @Test
        @DisplayName("success")
        @Order(0)
        public void success() {
            boardRepository.findById(1L)
                    .orElse(null);
        }
    }
}
