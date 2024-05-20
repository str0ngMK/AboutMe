package com.about.me.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.about.me.dto.BoardDto;
import com.about.me.entity.BoardEntity;
import com.about.me.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public List<BoardDto> getBoardList() {
		List<BoardEntity> list = boardRepository.findByDelYn(false);
		List<BoardDto> result = new ArrayList<>();
		for (BoardEntity entity : list) {
			BoardDto dto = new BoardDto();
			dto.setNo(entity.getNo());
			dto.setTitle(entity.getTitle());
			dto.setContent(entity.getContent());
			dto.setImage(entity.getImage());
			dto.setId(entity.getId());
			dto.setInsDate(entity.getInsDate());
			dto.setUpdDate(entity.getUpdDate());
			result.add(dto);
		}
		return result;
	}

}
