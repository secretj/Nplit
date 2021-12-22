package com.nplit.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class AdminVO {
   private int seq;
   private String writer;
   private String category;
   private String title;
   private String content;
   private Date regdate;
}