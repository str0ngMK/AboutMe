package com.about.me.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity(name = "question")
@DynamicInsert
@DynamicUpdate
@Getter
public class QuestionEntity {
	
	@Id
	@Column(name = "no")
	private long no;
	
	@Column(name = "chapter")
	private long chapter;
	
	@Column(name = "choice")
	private long choice;
	
	@Column(name = "message")
	private String message;

}
