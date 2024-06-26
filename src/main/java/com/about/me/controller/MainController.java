package com.about.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.about.me.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/board/list")
	public String boardList() {
		return "board_list";
	}
	
	@GetMapping("/board")
	public String board(@RequestParam (name="no") long no) {
		if (boardService.isBoard(no)) {
			return "board";
		}
		return "/error";
	}
	
	@GetMapping("/board/write")
	public String boardWrite(HttpServletRequest request, HttpServletResponse response) {
		boardService.deleteCookie(request, response);
		return "board_write";
	}
	
	@GetMapping("/board/modify")
	public String boardModify(HttpSession session, @RequestParam(name = "no") long no) {
		System.out.println("no = " + no);
		if(session.getAttribute("result") != null && (boolean)session.getAttribute("result") && ((long)session.getAttribute("board") == no)) {
			return "board_modify";
		}
		return "/error";
	}
	
}
