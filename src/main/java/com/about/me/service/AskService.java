package com.about.me.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Map<String, Object> message(long choice) {
		Map<String, Object> result = new HashMap<>();
		List<AnswerEntity> answer = answerRepository.findByChapter(choice);
		choice = answer.get(0).getChoice();
		List<QuestionEntity> question = questionRepository.findByChapter(choice);
		result.put("answer", answer);
		result.put("question", question);
		return result;
	}
}
