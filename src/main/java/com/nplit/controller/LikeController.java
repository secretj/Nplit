package com.nplit.controller; 

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nplit.service.LikeService;
import com.nplit.vo.BoardVO;
import com.nplit.vo.Criteria;
import com.nplit.vo.LikeVO;
import com.nplit.vo.MemberVO;
import com.nplit.vo.PageVO;

@Controller
public class LikeController {
   
   @Autowired
   private LikeService likeService;
   
   @RequestMapping("/insertLike")
   @ResponseBody
   public String insertLike(HttpSession session, HttpServletResponse response,LikeVO vo) throws JsonProcessingException {
      ObjectMapper mapper = new ObjectMapper();
      System.out.println(vo.getSeq());
      MemberVO loginInfo = (MemberVO)session.getAttribute("login");
      vo.setMemberId(loginInfo.getMemberId());
      likeService.insertLike(vo);
      likeService.updateLike(vo);
      //favorite count 조회
      int count = likeService.likeCountUpDown(vo);
           
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("count", count);
      String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap);
      System.out.println(json);
      return json;
   }
   
   @RequestMapping("/deleteLike")
   @ResponseBody
   public String deleteLike(LikeVO vo, HttpSession session) throws JsonProcessingException {
      ObjectMapper mapper = new ObjectMapper();
      MemberVO loginInfo = (MemberVO)session.getAttribute("login");
      vo.setMemberId(loginInfo.getMemberId());
      likeService.deleteLike(vo);
      likeService.updateLike(vo);
      
      int count = likeService.likeCountUpDown(vo);
      HashMap<String,Object>hasMap = new HashMap<String,Object>();
      hasMap.put("count", count);
      String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hasMap);
      return json; 
      
   }
   
   @RequestMapping("/mydeleteLike")
   public String mydeleteLike(LikeVO vo, HttpSession session) {
      MemberVO loginInfo = (MemberVO)session.getAttribute("login");
      vo.setMemberId(loginInfo.getMemberId());
      likeService.deleteLike(vo);
      likeService.updateLike(vo);
      return "redirect:/details?seq=" + vo.getSeq(); 
   }
   
// //내가찜한거
   @RequestMapping(value = "/sharing_like")
   public String sharing_like(Model model, HttpSession session, LikeVO vo, BoardVO bvo, Criteria cri){
      MemberVO likeInfo = (MemberVO)session.getAttribute("login");
      String likeId = likeInfo.getMemberId();
      
      Map<String,Object>paramMap = new HashMap<String,Object>();
      
      paramMap.put("likeId", likeId);
      paramMap.put("board", bvo);
      cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());
      paramMap.put("criteria", cri);
      //    model.addAttribute("board",bvo);
      
      cri.setAmount(4);
     int total = likeService.getLikeCount(likeId);
     
      model.addAttribute("pageMaker", new PageVO(cri, total));
      model.addAttribute("selectLikeList",likeService.selectLikeList(paramMap));
      model.addAttribute("getLikeCount",likeService.getLikeCount(likeId));
      model.addAttribute("mylist_count", total);
      /* System.out.println(likeService.getLikeCount(vo)); */
      return "/mypage/sharing_like";
   }
   
   
}