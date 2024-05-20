package com.about.me.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/board/list")
	public String boardList() {
		return "board_list";
	}
	
	@GetMapping("/board")
	public String board() {
		return "board";
	}
	
//	@GetMapping("/nav")
//	public String nav() {
//		return "nav/nav";
//	}
	
}
