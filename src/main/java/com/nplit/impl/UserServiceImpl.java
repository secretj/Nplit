package com.nplit.impl;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nplit.mapper.UserMapper;
import com.nplit.persistence.UserDAO;
import com.nplit.service.UserService;
import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

@Service
public class UserServiceImpl implements UserService {
   @Inject
   UserDAO dao;
   
   SqlSession sqlSession;
   
	
	@Autowired
	private UserMapper userMapper;
	
	//회원가입
	@Override
	public void join(MemberVO vo) {
		userMapper.join(vo);
	}
	
	//회원정보 수정
   @Override
   public void profileUpdate(MemberVO vo) {
        userMapper.profileUpdate(vo);
   }
   
   // 비밀번호 수정
   @Override
   public void pwdUpdate(MemberVO vo) {
      userMapper.pwdUpdate(vo);
   }
   
   // 회원탈퇴
   @Override
   public void memberDelete(MemberVO vo) {
      userMapper.memberDelete(vo);
   }
   
   //회원정보 조회
   @Override
   public MemberVO LoginInfo(MemberVO vo) {
        return userMapper.LoginInfo(vo);
   }
	
	//로그인
   @Override
   public MemberVO login(MemberVO dto) {
       return dao.login(dto);
   }

   @Override
   public void keepLogin(String memberId, String sessionId, Date next) {

       dao.keepLogin(memberId, sessionId, next);
   }

   @Override
   public MemberVO checkUserWithSessionKey(String sessionId) {
       return dao.checkUserWithSessionKey(sessionId);
   }
   
   //예진 구글 회원가입
   @Override
   public void joinMemberByGoogle(MemberVO vo) {
      //dao.join(vo); 
      //원래는 dao 쓰는건데 이렇게 해도 상관 없을 듯
      dao.join(vo);
   }

   //예진 구글 로그인
   @Override
   public MemberVO loginMemberByGoogle(MemberVO vo) {
      MemberVO returnVO = null;
      try {
         returnVO = dao.readMemberWithIDPW(vo.getMemberId(), vo.getPassword()); // 여기서 안되는거
               } catch (Exception e) {

         e.printStackTrace();
         returnVO = null; // 실행하다 문제가 생겼을때 해당 데이터를 보내지않겠다는 의미 = 예외처리
      }
      return returnVO;
   }

 //*******************  예진 중복검사  **************************
   
   // 예진 아이디 중복 검사
   @Override
   public int idChk(MemberVO vo) throws Exception {
      /*
       * int result = dao.idChk(vo); return result;
       */
      System.out.println("UserServiceimpl 에서 반환 값: " + userMapper.idChk(vo));
      return userMapper.idChk(vo);
   }

   
   //예진 닉네임 중복 검사
   @Override
   public int nicknameChk(MemberVO vo) throws Exception {
      // TODO Auto-generated method stub
      System.out.println("UserServiceimpl 에서 반환 값: " + userMapper.nicknameChk(vo));
      return userMapper.nicknameChk(vo);
   }
   
   //예진 로그인 검사
   @Override
   public int loginChk(MemberVO vo) throws Exception {
    return userMapper.loginChk(vo);
   }
   
   //비밀번호 찾기 이메일발송
   @Override
   public void sendEmail(MemberVO vo, String div) throws Exception {
      
   // Mail Server 설정
      String charSet = "utf-8";
      String hostSMTP = "smtp.gmail.com"; //네이버 이용시 smtp.naver.com
      String hostSMTPid = "qkrwlsgud890@gmail.com";
      String hostSMTPpwd = "cnsaks3305!@";

      // 보내는 사람 EMail, 제목, 내용
      String fromEmail = "qkrwlsgud890@gmail.com";
      String fromName = "NPLIT";
      String subject = "";
      String msg = "";

      if(div.equals("findpw")) {
         subject = "N플릿 임시 비밀번호 입니다.";
         msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
         msg += "<h3 style='color: blue;'>";
         msg += vo.getMemberId() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
         msg += "<p>임시 비밀번호 : ";
         msg += vo.getPassword() + "</p></div>";
      }

      

      // 받는 사람 E-Mail 주소
      String mail = vo.getEmail();
  
         HtmlEmail email = new HtmlEmail();
         email.setDebug(true);
         email.setCharset(charSet);
         email.setSSL(true);
         email.setHostName(hostSMTP);
         email.setSmtpPort(465); //네이버 이용시 587

         email.setAuthentication(hostSMTPid, hostSMTPpwd);
         email.setTLS(true);
         email.addTo(mail, charSet);
         email.setFrom(fromEmail, fromName, charSet);
         email.setSubject(subject);
         email.setHtmlMsg(msg);
         email.send();
      
      }

  @Override
  public String findPw(MemberVO vo) throws Exception {
                
      MemberVO findMember=userMapper.LoginInfo(vo);
      findMember.setMemberId(vo.getMemberId());
      System.out.println("========"+findMember);
      System.out.println(vo);
     //MemberVO ck = dao.LoginInfo(vo.getMemberId());
     // 가입된 아이디가 없으면
     if((findMember.getMemberId()) == null) {
        return "등록되지 않은 아이디입니다.";

     }
     // 가입된 이메일이 아니면
     else if(!vo.getEmail().equals(findMember.getEmail())) {
        return "등록되지 않은 이메일입니다.";
        
     }else {
        // 임시 비밀번호 생성
        String pw = "";
        for (int i = 0; i < 12; i++) {
           pw += (char) ((Math.random() * 26) + 97);
        }
        vo.setPassword(pw);
        // 비밀번호 변경
        dao.updatePw(vo);
        // 비밀번호 변경 메일 발송
        sendEmail(vo, "findpw");
   
            return "이메일로 임시 비밀번호를 발송하였습니다.";
    
         }
         
   }
        
        //문의하기 인설트
    @Override
    public void question(QueVO vo, HttpServletRequest request) {
       dao.question(vo);
       
    }

    
    //내가 등록한 문의 리스트뽑아오기
        public List<QueVO> selectMyQuestion(String memberId) {
           List<QueVO> listtest = userMapper.selectMyQuestion(memberId);
           return listtest;
        }
   
        //문의 상세보기
    public MemberVO selectOtherProfile(String memberId) {
       return userMapper.selectOtherProfile(memberId);
    }
   
        
   
}