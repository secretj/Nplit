package com.nplit.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;


//member
@Data
public class MemberVO {
	   private String memberId;
	   private String name;
	   private String password;
	   private String oldPassword;   
	   private String nickname;
	   private String email;
	   private String phone;
	   private String address;
       private String filePath;
       private String fileName;
       private String fullFilePath;
	   private String proMsg;
	   private String locate_x;
	   private String locate_y;
	   private int blacklist;
	   private int role;
	   private Date regdate;
	   private Date updatedate;
	   private String enabled;
	   
	   private String sessionId;
	   private String sessionkey;
	   private Date sessionlimit;
	   private String isUseCookie;
	   private MultipartFile uploadFile;
}
   
   