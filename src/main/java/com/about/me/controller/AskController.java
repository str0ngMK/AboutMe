package com.about.me.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.about.me.service.AskService;

@RestController
@RequestMapping("/chat")
public class AskController {

	@Autowired
	private AskService askService;

	@GetMapping("/ask")
	public ResponseEntity<Map<String, Object>> getMessage(@RequestParam(name = "num") int num) {
		return ResponseEntity.ok().body(askService.message(num));
	}
}
