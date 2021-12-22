package com.nplit.vo;

import java.sql.Date;
import lombok.Data;

@Data
public class BoardVO {
   private int seq; //게시글 번호
   private String title; //게시글 제목
   private String writer; //작성자
   private String category; //카테고리
   private String tag; //태그
   private int hit; //조회수
   private String answer; //답변
   private String contents; //내용
   private String price; //금액
   private String productname; //상품명
   private String maxNum; //최대 참여인원
   private String address; //장소
   private String productlink; //상품링크
   private String cover_img; //상품 대표 이미지
   private String deleteYN; //글 삭제여부
   private Date regDate; //글 등록일
   private Date updateDate; //글 수정일
   private String file_no; //파일번호
   private String status; //글 답변여부
   private String totalNum; //글 답변여부
   private String favorite; //찜 등록
   private String reported_id; //신고자
//   private String SearchCondition;
   private String SearchKeyword; //제품명
   private String SearchCategory; // 카테고리명
   private String SearchAddress; // 지역명
}