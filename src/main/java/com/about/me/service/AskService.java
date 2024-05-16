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
	
	public Map<String, Object> message(int num) {
		Map<String, Object> result = new HashMap<>();
		List<AnswerEntity> answer = answerRepository.findByChapter(num);
		List<QuestionEntity> question = questionRepository.findByChapter(num);
		result.put("answer", answer);
		result.put("question", question);
		return result;
	}
}
