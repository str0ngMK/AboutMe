package com.about.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.about.me.dto.AskDto;
import com.about.me.service.AskService;
import com.about.me.util.IpConversion;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/chat")
@Log4j2
public class AskController {

	@Autowired
	private AskService askService;
	
	@Autowired
	private IpConversion ipConversion;

	@GetMapping("/ask")
	public ResponseEntity<AskDto> getMessage(HttpServletRequest request, @RequestParam(name="choice") long choice) {
		String ipv4 = ipConversion.convertToIPv4(request.getRemoteAddr());
		log.info("[ASK] REQUEST IP: " + ipv4);
		return ResponseEntity.ok().body(askService.message(choice));
	}
}
