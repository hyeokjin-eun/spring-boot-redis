package com.example.board.board.infra;

import com.example.board.board.dto.enums.BoardStatus;
import com.example.board.board.infra.entity.Board;
import com.example.board.board.infra.entity.BoardCate;
import com.example.board.board.infra.repository.BoardCateRepository;
import com.example.board.board.infra.repository.BoardRepository;
import com.example.board.board.exception.BoardNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
@Nested
@DisplayName("게시판 Repository")
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCateRepository boardCateRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Nested
    @DisplayName("생성")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class SaveTest {

        private Board board;
        private  final BoardStatus STATUS = BoardStatus.ACTIVE;

        @BeforeEach
        public void setUp() {
            String TITLE = "title";
            String CONTENT = "content";
            board = Board.builder()
                    .title(TITLE)
                    .content(CONTENT)
                    .status(STATUS)
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

        private final BoardStatus STATUS = BoardStatus.ACTIVE;

        @BeforeEach
        public void setUp() {
            for (int i = 0; i < 10; i++) {
                testEntityManager.persist(Board.builder()
                        .title("title" + (i + 1))
                        .content("content" + (i + 1))
                        .status(STATUS)
                        .build());
            }
        }

        @Test
        @DisplayName("success")
        @Order(0)
        public void success() {
            boardRepository.findById(1L)
                    .orElseThrow(BoardNotFoundException::new);
        }
    }

    @Nested
    @DisplayName("카테고리 조회")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class FindAllTest {

        @BeforeEach
        public void setUp() {
            for (int i = 0; i < 10; i++) {
                testEntityManager.persist(BoardCate.builder()
                        .name("name" + i)
                        .build());
            }
        }

        @Test
        @DisplayName("success")
        @Order(0)
        public void success() {
            boardCateRepository.findAll();
        }
    }
}
