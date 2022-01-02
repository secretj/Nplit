package com.nplit.vo;

import lombok.Data;

//@Getter
//@Setter
@Data // Getter�� Setter 모두 생성하는 어노테이션
public class BoardFileVO {
	private int seq;
	private int fSeq;
	private String originalFileName;
	private String filePath;
	private long fileSize;
}
