package com.example.board.board.ui;

import com.example.board.board.application.BoardService;
import com.example.board.board.dto.enums.BoardStatus;
import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCateResponseDto;
import com.example.board.board.dto.response.BoardCateResponseListDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import com.example.board.board.dto.response.BoardSelectResponseDto;
import com.example.board.board.exception.BoardCateNotFoundException;
import com.example.board.board.exception.BoardNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
@Nested
@DisplayName("게시판 Controller")
public class BoardControllerTest {

    public final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @Nested
    @DisplayName("생성")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class CreateTest {
        private final String URL = "/board";
        private final long SEQ = 1L;
        private final String TITLE = "title";
        private final String CONTENT = "content";
        private final String STATUS = "ACTIVE";
        private BoardCreateRequestDto boardCreateRequestDto;

        @BeforeEach
        public void setUp() {
            boardCreateRequestDto = BoardCreateRequestDto.builder()
                    .title(TITLE)
                    .content(CONTENT)
                    .build();

            BoardCreateResponseDto boardCreateResponseDto = BoardCreateResponseDto.builder()
                    .seq(SEQ)
                    .title(TITLE)
                    .content(CONTENT)
                    .status(BoardStatus.ACTIVE)
                    .build();

            given(boardService.create(any(BoardCreateRequestDto.class))).willReturn(boardCreateResponseDto);
        }

        @Test
        @DisplayName("Success")
        @Order(0)
        public void create_success() throws Exception {
            mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.seq").value(SEQ))
                    .andExpect(jsonPath("$.data.title").value(TITLE))
                    .andExpect(jsonPath("$.data.content").value(CONTENT))
                    .andExpect(jsonPath("$.data.status").value(STATUS))
                    ;

            verify(boardService).create(any(BoardCreateRequestDto.class));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Title Null And Empty")
        @Order(1)
        public void create_title_null_and_empty(String title) throws Exception {
            boardCreateRequestDto.setTitle(title);
            mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                    .andExpect(status().isBadRequest())
            ;
        }

        @ParameterizedTest
        @ValueSource(strings = {"012345678901234567890123456789012345678901234567890123456789"})
        @DisplayName("Title Validation Check")
        @Order(2)
        public void create_title_validation_check(String title) throws Exception {
            boardCreateRequestDto.setTitle(title);
            mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                    .andExpect(status().isBadRequest())
            ;
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Content Null And Empty")
        @Order(3)
        public void create_content_null_and_empty(String content) throws Exception {
            boardCreateRequestDto.setContent(content);
            mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
            ;
        }
    }

    @Nested
    @DisplayName("검색")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class SelectTest {
        private final String URL = "/board/1";
        private BoardSelectResponseDto boardSelectResponseDto;

        @BeforeEach
        public void setUp() {
            String TITLE = "title";
            String CONTENT = "content";
            boardSelectResponseDto = BoardSelectResponseDto.builder()
                    .seq(1L)
                    .title(TITLE)
                    .content(CONTENT)
                    .build();
        }

        @Test
        @DisplayName("Success")
        @Order(0)
        public void select_success() throws Exception {
            given(boardService.select(anyLong())).willReturn(boardSelectResponseDto);
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isNotEmpty())
            ;
        }

        @Test
        @DisplayName("Not Found")
        @Order(1)
        public void select_not_found() throws Exception {
            given(boardService.select(anyLong())).willThrow(BoardNotFoundException.class);
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isNotFound())
            ;
        }
    }

    @Nested
    @DisplayName("카테고리 조회")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class CateTest {
        private final String URL = "/board/cates";
        private final String[] cateNameList = new String[]{
                "예술", "개발", "소셜", "학습", "연애"
        };
        private BoardCateResponseDto boardCateResponseDto;

        @BeforeEach
        public void setUp() {
            BoardCateResponseDto boardCateResponseDto = new BoardCateResponseDto();
            List<BoardCateResponseListDto> boardCateResponseListDtoList = new ArrayList<>();
            for (int i = 0; i < cateNameList.length; i++) {
                boardCateResponseListDtoList.add(BoardCateResponseListDto.builder()
                        .seq(i + 1)
                        .name(cateNameList[i])
                        .build());
            }

            boardCateResponseDto.setList(boardCateResponseListDtoList);
            this.boardCateResponseDto = boardCateResponseDto;
        }

        @Test
        @DisplayName("Success")
        @Order(0)
        public void success() throws Exception {
            given(boardService.cates()).willReturn(boardCateResponseDto);
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andExpect(jsonPath("$.data.list").isNotEmpty());
            verify(boardService).cates();
        }

        @Test
        @DisplayName("Not Found")
        @Order(1)
        public void cate_not_found() throws Exception {
            given(boardService.cates()).willThrow(BoardCateNotFoundException.class);
            mockMvc.perform(get(URL)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isNotFound());
        }
    }
}

