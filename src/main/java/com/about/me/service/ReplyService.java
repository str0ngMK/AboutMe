package com.about.me.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.about.me.dto.ReplyDto;
import com.about.me.dto.req.ReqReplyDto;
import com.about.me.entity.ReplyEntity;
import com.about.me.repository.ReplyRepository;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;
	
	public boolean saveReply(ReqReplyDto body) {
		ReplyDto dto = new ReplyDto();
		try {
			String author = body.getAuthor();
			String content = body.getContent();
			long boardNo = body.getBoardNo();
			String replyPwd = body.getReplyPwd();
			
			dto.setAuthor(author);
			dto.setContent(content);
			dto.setBoardNo(boardNo);
			dto.setReplyPwd(replyPwd);
			
			replyRepository.saveAndFlush(dto.toEntity());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<ReqReplyDto> getReply(long no) {
		List<ReqReplyDto> result = new ArrayList<>();
		List<ReplyEntity> replyResult = replyRepository.findByBoardNoAndDelYn(no, false);
		if (replyResult.size() > 0) {
			for (ReplyEntity entity : replyResult) {
				ReqReplyDto dto = new ReqReplyDto();
				dto.setAuthor(entity.toDto().getAuthor());
				dto.setContent(entity.toDto().getContent());
				dto.setInsDate(entity.getInsDate());
				result.add(dto);
			}
		}
		return result;
	}

}
