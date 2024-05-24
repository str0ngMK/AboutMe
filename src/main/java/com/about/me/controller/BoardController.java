package com.about.me.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.about.me.dto.req.ReqBoardDto;
import com.about.me.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/list/get")
	public ResponseEntity<List<ReqBoardDto>> getBoardList() {
		System.out.println("/board/list");
		return ResponseEntity.ok().body(boardService.getBoardList());
	}

	@GetMapping("/get")
	public ResponseEntity<ReqBoardDto> getBoard(@RequestParam(name = "no") long no){
		ReqBoardDto result = boardService.getBoard(no);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/image-upload")
	public ResponseEntity<String> uploadEditorImage(@RequestParam(name = "image") MultipartFile image) {
		return ResponseEntity.ok().body(boardService.uploadImage(image));
	}
	
	@GetMapping("/image-print")
    public ResponseEntity<byte[]> printEditorImage(@RequestParam(name = "filename") String filename) {
    	return ResponseEntity.ok().body(boardService.printImage(filename));
    }
    
    @PostMapping("/save")
    public ResponseEntity<?> saveBoard(@RequestBody ReqBoardDto body){
    	return ResponseEntity.ok().body(boardService.saveBoard(body));
    }
}
