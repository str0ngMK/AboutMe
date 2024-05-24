package com.about.me.dto.req;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReqBoardDto {
	private long no;
	private String title;
	private String content;
	private String preview;
	private String image;
	private String author;
	private String boardPwd;
	private boolean delYn;
	private Timestamp insDate;
	private Timestamp updDate;
}
