package com.nplit.vo;

import lombok.Data;

@Data

public class AttachVO {
   private int fileno;
   private int seq;
   private long filesize;
   private String filename;
   private String uploadpath;
}