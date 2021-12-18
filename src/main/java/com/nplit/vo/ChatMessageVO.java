package com.nplit.vo;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ChatMessageVO {
	private int msgSeq;
	private int roomId;
	private String sendId;
	private String message;
	private Timestamp regdate;
	private String fullFilePath;
}
