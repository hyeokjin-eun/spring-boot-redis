package com.example.board.board.ui;

import com.example.board.board.application.BoardService;
import com.example.board.board.dto.request.BoardCreateRequestDto;
import com.example.board.board.dto.response.BoardCreateResponseDto;
import com.example.board.board.dto.response.BoardSelectResponseDto;
import com.example.board.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<BoardCreateResponseDto>> create(
            @Valid @RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        return ResponseDto.success(boardService.create(boardCreateRequestDto));
    }

    @GetMapping("{seq}")
    public ResponseEntity<ResponseDto<BoardSelectResponseDto>> select(
            @PathVariable long seq) {
        return ResponseDto.success(boardService.select(seq));
    }
}
