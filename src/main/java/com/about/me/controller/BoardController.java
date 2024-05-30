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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.about.me.dto.req.ReqBoardDto;
import com.about.me.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
//		if (result == null || result.isDelYn()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/image-upload")
	public ResponseEntity<String> uploadEditorImage(@RequestParam(name = "image") MultipartFile image, HttpServletResponse response) {
		return ResponseEntity.ok().body(boardService.uploadImage(image, response));
	}
	
	@GetMapping("/image-print")
    public ResponseEntity<byte[]> printEditorImage(@RequestParam(name = "filename") String filename) {
    	return ResponseEntity.ok().body(boardService.printImage(filename));
    }
    
    @PostMapping("/save")
    public ResponseEntity<?> saveBoard(@RequestBody ReqBoardDto body, HttpServletRequest request, HttpServletResponse response){
    	return ResponseEntity.ok().body(boardService.saveBoard(body, request, response));
    }
    
    @PostMapping("/pwdCheck")
    public ResponseEntity<?> checkBoardPwd(HttpSession session, @RequestBody ReqBoardDto body){
    	return ResponseEntity.ok().body(boardService.checkBoardPwd(session, body.getNo(), body.getBoardPwd()));
    }
    
    @PutMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteBoard(HttpSession session){
    	Map<String, Object> result = new HashMap<>();
//    	boolean isPwdCheck = boardService.checkBoardPwd(body.getNo(), body.getBoardPwd());
//    	if (!isPwdCheck) {
//    		result.put("result", false);
//    		result.put("message", "비밀번호를 확인 해 주세요.");
//    		return ResponseEntity.ok().body(result);
//    	}
    	if (session.getAttribute("result") == null || !(boolean) session.getAttribute("result")) {
    		result.put("result", false);
    		result.put("message", "잘못 된 요청 입니다.");
    		return ResponseEntity.ok().body(result);
    	}
    	boolean isDeleteResult =  boardService.deleteBoard((long)session.getAttribute("board"));
    	if (!isDeleteResult) {
    		result.put("result", false);
    		result.put("message", "문제가 발생 하였습니다. 잠시 후 다시 시도 해 주세요.");
    		return ResponseEntity.ok().body(result);
    	}
    	// 세션 삭제
    	session.invalidate();
    	
    	result.put("result", true);
    	result.put("message", "");
    	return ResponseEntity.ok().body(result);
    }
    
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object> > updateBoard(HttpSession session, @RequestBody ReqBoardDto body, HttpServletRequest request, HttpServletResponse response){
    	Map<String, Object> result = new HashMap<>();
    	if(session.getAttribute("result") == null || !(boolean) session.getAttribute("result")) {
    		result.put("result", false);
    		result.put("message", "잘못 된 요청 입니다.");
    		return ResponseEntity.ok().body(result);
    	}
    	boolean isUpdateResult = boardService.updateBoard(session, body, request, response);
    	if(!isUpdateResult) {
    		result.put("result", false);
    		result.put("message", "문제가 발생 하였습니다. 잠시 후 다시 시도 해 주세요.");
    		return ResponseEntity.ok().body(result);
    	}
    	// 세션 삭제
    	session.invalidate();
    	
    	result.put("result", true);
    	result.put("message", "");
    	return ResponseEntity.ok().body(result);
    }
    
}
