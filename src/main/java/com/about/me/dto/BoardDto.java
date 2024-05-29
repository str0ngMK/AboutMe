package com.about.me.dto;

import com.about.me.entity.BoardEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardDto {
	private long no;
	private String title;
	private String content;
	private String preview;
	private String image;
	private String tag;
	private String author;
	private String boardPwd;
	private boolean delYn;
	
	@Builder
	public BoardDto(long no, String title, String content, String preview, String image, String tag, String author, String boardPwd, boolean delYn) {
		this.no=no;
		this.title=title;
		this.content=content;
		this.preview=preview;
		this.image=image;
		this.tag=tag;
		this.author=author;
		this.boardPwd=boardPwd;
		this.delYn=delYn;
	}
	
	public BoardEntity toEntity() {
		return BoardEntity.builder()
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
