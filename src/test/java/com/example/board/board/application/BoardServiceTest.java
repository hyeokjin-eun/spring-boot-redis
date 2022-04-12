package com.example.board.board.application;

import com.example.board.board.application.impl.BoardServiceImpl;
import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Nested
@DisplayName("게시판 Service")
public class BoardServiceTest {

    @InjectMocks
    private BoardServiceImpl boardService;

    @Nested
    @DisplayName("생성")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class CreateTest {

        private BoardCreateRequestDto boardCreateRequestDto;

        @BeforeEach
        public void setUp() {
            boardCreateRequestDto = BoardCreateRequestDto.builder()
                    .title("title")
                    .content("content")
                    .build();
        }

        @Test
        @DisplayName("Success")
        @Order(0)
        public void create_success() {
            BoardCreateResponseDto boardCreateResponseDto = boardService.create(boardCreateRequestDto);
            assertThat(boardCreateResponseDto).isNotNull();
            assertThat(boardCreateResponseDto.getTitle()).isEqualTo(boardCreateRequestDto.getTitle());
            assertThat(boardCreateResponseDto.getContent()).isEqualTo(boardCreateRequestDto.getContent());
        }
    }
}
