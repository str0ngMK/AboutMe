package com.about.me.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.about.me.dto.req.ReqReplyDto;
import com.about.me.service.ReplyService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/get")
	public ResponseEntity<?> getReply(@RequestParam(name="no") long no){
		return ResponseEntity.ok().body(replyService.getReply(no));
	}
	
	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveReply(@RequestBody ReqReplyDto body){
		Map<String, Object> result = new HashMap<>();
		boolean isSaveResult = replyService.saveReply(body);
		if(!isSaveResult) {
			result.put("result", false);
			result.put("message", "잠시 후 다시 시도해 주세요.");
			return ResponseEntity.ok().body(result);
		}
		result.put("result", true);
		result.put("message", "");
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/pwdCheck")
	public ResponseEntity<?> checkReplyPwd (HttpSession session, @RequestBody ReqReplyDto body) {
		return ResponseEntity.ok().body(replyService.checkReplyPwd(session, body.getNo(), body.getReplyPwd()));
	}
	
	@PutMapping("/delete")
	public ResponseEntity<?> deleteReply(HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		if(session.getAttribute("result") == null || !(boolean) session.getAttribute("result")) {
			result.put("result", false);
    		result.put("message", "잘못 된 요청 입니다.");
    		return ResponseEntity.ok().body(result);
		}
		boolean isDeleteResult = replyService.deleteReply((long) session.getAttribute("reply"));
		if (!isDeleteResult) {
			result.put("result", false);
    		result.put("message", "문제가 발생 하였습니다. 잠시 후 다시 시도 해 주세요.");
    		return ResponseEntity.ok().body(result);
		}
		
		session.invalidate();
		
		result.put("result", true);
    	result.put("message", "");
    	return ResponseEntity.ok().body(result);
	}
}
