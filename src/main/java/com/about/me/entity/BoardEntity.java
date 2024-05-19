package com.about.me.entity;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity(name = "board")
@DynamicInsert
@DynamicUpdate
@Getter
public class BoardEntity {

	@Id
	@Column(name = "no")
	private long no;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "id")
	private String id;
	
	@Column(name = "board_pwd")
	private String boardPwd;
	
	@Column(name = "del_yn")
	private boolean delYn;
	
	@Column(name = "ins_date")
	private Date insDate;
	
	@Column(name = "upd_date")
	private Date updDate;
}
