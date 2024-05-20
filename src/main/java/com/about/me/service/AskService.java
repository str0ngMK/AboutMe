package com.about.me.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.about.me.dto.AskDto;
import com.about.me.entity.AnswerEntity;
import com.about.me.entity.QuestionEntity;
import com.about.me.repository.AnswerRepository;
import com.about.me.repository.QuestionRepository;

@Service
public class AskService {
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	public AskDto message(long choice) {
		AskDto dto = new AskDto();
		List<AnswerEntity> answer = answerRepository.findByChapter(choice);
		choice = answer.get(0).getChoice();
		List<QuestionEntity> question = questionRepository.findByChapter(choice);
		dto.setAnswer(answer);
		dto.setQuestion(question);
		return dto;
	}
}
