package com.about.me.dto;

import com.about.me.entity.ReplyEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyDto {
	private long no;
	private String author;
	private String content;
	private long boardNo;
	private boolean delYn;
	private String replyPwd;

	@Builder
	public ReplyDto(long no, String author, String content, long boardNo, boolean delYn, String replyPwd) {
		this.no=no;
		this.author=author;
		this.content=content;
		this.boardNo=boardNo;
		this.delYn=delYn;
		this.replyPwd=replyPwd;
	}
	
	public ReplyEntity toEntity() {
		return ReplyEntity.builder()
				.no(no)
				.author(author)
				.content(content)
				.boardNo(boardNo)
				.delYn(delYn)
				.replyPwd(replyPwd)
				.build();
	}
}
