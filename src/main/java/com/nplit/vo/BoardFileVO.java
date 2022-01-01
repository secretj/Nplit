package com.nplit.vo;

import lombok.Data;

//@Getter
//@Setter
@Data // Getter�� Setter ��� �����ϴ� ������̼�
public class BoardFileVO {
	private int seq;
	private int fSeq;
	private String originalFileName;
	private String filePath;
	private long fileSize;
}
