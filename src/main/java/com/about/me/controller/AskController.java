package com.about.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.about.me.service.AskService;

@RestController
@RequestMapping("/chat")
public class AskController {
	
	@Autowired
	private AskService askService;
	
	@GetMapping("/ask")
	public ResponseEntity<String> getMessage(){
		askService.message();
		return null;
	}
}
