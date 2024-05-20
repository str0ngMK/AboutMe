package com.about.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.about.me.dto.BoardDto;
import com.about.me.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public ResponseEntity<List<BoardDto>> getBoardList() {
		System.out.println("/board/list");
		return ResponseEntity.ok().body(boardService.getBoardList());
	}
}
