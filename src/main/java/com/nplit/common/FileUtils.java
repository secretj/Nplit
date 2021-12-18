package com.nplit.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nplit.vo.AttachVO;

public class FileUtils {
   public List<AttachVO> parseFileInfo(int seq, HttpServletRequest request, 
         MultipartHttpServletRequest mhsr) throws IOException {
      if(ObjectUtils.isEmpty(mhsr)) {
         return null;
      }
      
      List<AttachVO> fileList = new ArrayList<AttachVO>();
      
      
      //서버의 절대 경로 얻기
      String root_path = request.getSession().getServletContext().getRealPath("/");
      String attach_path = "/upload/";
      
      //위 경로의 폴더가 없으면 폴더 생성
      File file = new File(root_path + attach_path);
      if(file.exists() == false) {
         file.mkdir();
      }
      
      //파일 이름들을 iterator로 담음
      Iterator<String> iterator = mhsr.getFileNames();
      
      while(iterator.hasNext()) {
         //파일명으로 파일 리스트 꺼내오기
         List<MultipartFile> list = mhsr.getFiles(iterator.next());
         
         //파일 리스트 개수 만큼 리턴할 파일 리스트에 담아주고 생성
         for(MultipartFile mf : list) {
            if(mf.getSize() > 0) {
               AttachVO attach = new AttachVO();
               attach.setSeq(seq);
               attach.setFilesize(mf.getSize());
               attach.setFilename(mf.getOriginalFilename());
               attach.setUploadpath(root_path + attach_path);
               fileList.add(attach);

               file = new File(root_path + attach_path + mf.getOriginalFilename());
               mf.transferTo(file);
            } else {
               fileList = null;
            }
         }
      }
      return fileList;
   }
}