package com.about.me.dto;

import java.util.Date;
import java.util.List;

import com.about.me.entity.BoardEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
	private long no;
	private String title;
	private String content;
	private String image;
	private String id;
	private Date insDate;
	private Date updDate;
}
