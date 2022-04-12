package com.example.board.board.application;

import com.example.board.board.application.impl.BoardServiceImpl;
import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import com.example.board.board.dto.response.BoardSelectResponseDto;
import com.example.board.board.infra.entity.Board;
import com.example.board.board.infra.repository.BoardRepository;
import com.example.board.common.error.exception.BoardNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Nested
@DisplayName("게시판 Service")
public class BoardServiceTest {

    @InjectMocks
    private BoardServiceImpl boardService;

    @Mock
    private BoardRepository boardRepository;

    @Nested
    @DisplayName("생성")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class CreateTest {

        private BoardCreateRequestDto boardCreateRequestDto;

        @BeforeEach
        public void setUp() {
            String TITLE = "title";
            String CONTENT = "content";
            boardCreateRequestDto = BoardCreateRequestDto.builder()
                    .title(TITLE)
                    .content(CONTENT)
                    .build();

            Board board = Board.builder()
                    .seq(1L)
                    .title(TITLE)
                    .content(CONTENT)
                    .build();

            given(boardRepository.save(any(Board.class))).willReturn(board);
        }

        @Test
        @DisplayName("Success")
        @Order(0)
        public void create_success() {
            BoardCreateResponseDto boardCreateResponseDto = boardService.create(boardCreateRequestDto);

            assertThat(boardCreateResponseDto).isNotNull();
            assertThat(boardCreateResponseDto.getSeq()).isNotZero();
            assertThat(boardCreateResponseDto.getTitle()).isEqualTo(boardCreateRequestDto.getTitle());
            assertThat(boardCreateResponseDto.getContent()).isEqualTo(boardCreateRequestDto.getContent());
            verify(boardRepository).save(any(Board.class));
        }
    }

    @Nested
    @DisplayName("검색")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class SelectTest {

        private Board board;

        @BeforeEach
        public void setUp() {
            String TITLE = "title";
            String CONTENT = "content";
            board = Board.builder()
                    .seq(1L)
                    .title(TITLE)
                    .content(CONTENT)
                    .build();
        }

        @Test
        @DisplayName("Success")
        @Order(0)
        public void select_success() {
            given(boardRepository.findById(anyLong())).willReturn(Optional.ofNullable(board));

            BoardSelectResponseDto boardSelectResponseDto = boardService.select(1L);

            assertThat(boardSelectResponseDto).isNotNull();
            assertThat(boardSelectResponseDto.getSeq()).isNotZero();
            assertThat(boardSelectResponseDto.getTitle()).isNotEmpty();
            assertThat(boardSelectResponseDto.getContent()).isNotEmpty();
            verify(boardRepository).findById(anyLong());
        }

        @Test
        @DisplayName("Not Found")
        @Order(1)
        public void select_not_found() {
            given(boardRepository.findById(anyLong())).willReturn(Optional.empty());
            assertThrows(BoardNotFoundException.class, () -> boardService.select(1L));
        }
    }
}
