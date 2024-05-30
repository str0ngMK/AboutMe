package com.about.me.dto.req;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReqReplyDto {
	private long no;
	private String author;
	private String content;
	private long boardNo;
	private boolean delYn;
	private String replyPwd;
	private Timestamp insDate;
	private Timestamp updDate;
}
