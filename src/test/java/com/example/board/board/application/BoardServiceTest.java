package com.example.board.board.application;

import com.example.board.board.application.impl.BoardServiceImpl;
import com.example.board.board.dto.enums.BoardStatus;
import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCateResponseDto;
import com.example.board.board.dto.response.BoardCateResponseListDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import com.example.board.board.dto.response.BoardSelectResponseDto;
import com.example.board.board.exception.BoardCateNotFoundException;
import com.example.board.board.infra.dao.BoardCateDao;
import com.example.board.board.infra.dao.BoardDao;
import com.example.board.board.infra.entity.Board;
import com.example.board.board.exception.BoardNotFoundException;
import com.example.board.board.infra.entity.BoardCate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
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
    private BoardDao boardDao;

    @Mock
    private BoardCateDao boardCateDao;

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
                    .status(BoardStatus.ACTIVE)
                    .build();

            given(boardDao.save(any(Board.class))).willReturn(board);
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
            assertThat(boardCreateResponseDto.getStatus()).isEqualTo(BoardStatus.ACTIVE);
            verify(boardDao).save(any(Board.class));
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
            given(boardDao.findById(anyLong())).willReturn(board);

            BoardSelectResponseDto boardSelectResponseDto = boardService.select(1L);

            assertThat(boardSelectResponseDto).isNotNull();
            assertThat(boardSelectResponseDto.getSeq()).isNotZero();
            assertThat(boardSelectResponseDto.getTitle()).isNotEmpty();
            assertThat(boardSelectResponseDto.getContent()).isNotEmpty();
            verify(boardDao).findById(anyLong());
        }

        @Test
        @DisplayName("Not Found")
        @Order(1)
        public void select_not_found() {
            given(boardDao.findById(anyLong())).willThrow(BoardNotFoundException.class);
            assertThrows(BoardNotFoundException.class, () -> boardService.select(1L));
        }
    }

    @Nested
    @DisplayName("카테고리 조회")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class CateTest {
        private final long[] SEQ_LIST = new long[]{
                1, 2, 3, 4, 5
        };
        private final String[] NAME_LIST = new String[]{
                "예술", "개발", "소셜", "학습", "연애"
        };

        private List<BoardCate> boardCateList;
        private BoardCateResponseDto boardCateResponseDto;

        @BeforeEach
        public void setUp() {
            List<BoardCate> boardCateList = new ArrayList<>();
            for (int i = 0; i < SEQ_LIST.length; i++) {
                boardCateList.add(BoardCate.builder()
                        .seq(SEQ_LIST[i])
                        .name(NAME_LIST[i])
                        .build());
            }

            this.boardCateList = boardCateList;
            BoardCateResponseDto boardCateResponseDto = new BoardCateResponseDto();
            List<BoardCateResponseListDto> boardCateResponseListDtoList = new ArrayList<>();
            for (int i = 0; i < SEQ_LIST.length; i++) {
                boardCateResponseListDtoList.add(BoardCateResponseListDto.builder()
                        .seq(SEQ_LIST[i])
                        .name(NAME_LIST[i])
                        .build());
            }

            boardCateResponseDto.setList(boardCateResponseListDtoList);
            this.boardCateResponseDto = boardCateResponseDto;
        }

        @Test
        @DisplayName("Success")
        @Order(0)
        public void success() {
            given(boardCateDao.findAll()).willReturn(boardCateList);
            BoardCateResponseDto boardCateResponseDto = boardService.cates();
            assertThat(boardCateResponseDto).isNotNull();
            for (int i = 0; i < boardCateResponseDto.getList().size(); i++) {
                assertThat(boardCateResponseDto.getList().get(i).getSeq()).isEqualTo(SEQ_LIST[i]);
                assertThat(boardCateResponseDto.getList().get(i).getName()).isEqualTo(NAME_LIST[i]);
            }

            verify(boardCateDao).findAll();
        }

        @Test
        @DisplayName("Cate List Empty")
        @Order(1)
        public void cate_list_empty() {
            given(boardCateDao.findAll()).willReturn(new ArrayList<>());
            assertThrows(BoardCateNotFoundException.class, () -> boardService.cates());
        }
    }
}
