package com.about.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.about.me.dto.BoardDto;
import com.about.me.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/list/get")
	public ResponseEntity<List<BoardDto>> getBoardList() {
		System.out.println("/board/list");
		return ResponseEntity.ok().body(boardService.getBoardList());
	}

	@GetMapping("/get")
	public ResponseEntity<BoardDto> getBoard(@RequestParam(name = "no") long no){
		BoardDto result = boardService.getBoard(no);
		System.out.println("result: " + result);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok().body(result);
	}
}
