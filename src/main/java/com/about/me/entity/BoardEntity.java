package com.about.me.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.about.me.dto.BoardDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "board")
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity extends BaseTimeEntity{

	@Id
	@Column(name = "no")
	private long no;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "preview")
	private String preview;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "tag")
	private String tag;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "board_pwd")
	private String boardPwd;
	
	@Column(name = "del_yn")
	private boolean delYn;
	
//	@Column(name = "ins_date")
//	private Date insDate;
//	
//	@Column(name = "upd_date")
//	private Date updDate;
	
	public BoardDto toDto() {
		return BoardDto.builder()
				.no(no)
				.title(title)
				.content(content)
				.preview(preview)
				.image(image)
				.tag(tag)
				.author(author)
				.boardPwd(boardPwd)
				.delYn(delYn)
				.build();
	}
}
