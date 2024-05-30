package com.about.me.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.about.me.dto.ReplyDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "reply")
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyEntity extends BaseTimeEntity{

	@Id
	@Column(name = "no")
	private long no;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "board_no")
	private long boardNo;
	
	@Column(name = "del_yn")
	private boolean delYn;
	
	@Column(name = "reply_pwd")
	private String replyPwd;
	
	
	public ReplyDto toDto() {
		return ReplyDto.builder()
				.no(no)
				.author(author)
				.content(content)
				.boardNo(boardNo)
				.delYn(delYn)
				.replyPwd(replyPwd)
				.build();
	}
}
