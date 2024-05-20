package com.about.me.dto;

import java.util.List;

import com.about.me.entity.AnswerEntity;
import com.about.me.entity.QuestionEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskDto {
	private List<AnswerEntity> answer;
	private List<QuestionEntity> question;
}
