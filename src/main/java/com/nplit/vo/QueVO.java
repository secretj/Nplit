package com.nplit.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class QueVO {
	private int seq;
	private String writer;
	private String category;
	private String title;
	private String content;
	private String answer;
	private Date regdate;
}