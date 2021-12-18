package com.nplit.service;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

public interface UserService {
	// 로그인
    public MemberVO login(MemberVO dto);
     
    // 자동로그인 체크한 경우에 사용자 테이블에 세션과 유효시간을 저장하기 위한 메서드
    public void keepLogin(String member_id, String sessionId, Date next);
     
    // 이전에 로그인한 적이 있는지, 즉 유효시간이 넘지 않은 세션을 가지고 있는지 체크한다.
    public MemberVO checkUserWithSessionKey(String sessionId);

	//회원가입
    void join(MemberVO vo);
	
	//개인정보수정
	void profileUpdate(MemberVO vo);
	
	//비밀번호 수정
    void pwdUpdate(MemberVO vo);
   
    //회원탈퇴
    void memberDelete(MemberVO vo);
	
    //로그인 회원 정보
	MemberVO LoginInfo(MemberVO vo);
	
	// 구글회원가입
   public void joinMemberByGoogle(MemberVO vo);

   // 구글로그인
   public MemberVO loginMemberByGoogle(MemberVO vo);

   
   //*********  예진 중복검사  ************
   //아이디 중복확인 체크
   public int idChk(MemberVO vo) throws Exception;
   
   //예진 닉네임 중복체크
   public int nicknameChk(MemberVO vo) throws Exception;
   
   //예진 로그인 검사
   public int loginChk(MemberVO vo)throws Exception;
   
   //이메일발송
   public void sendEmail(MemberVO vo, String div) throws Exception;
   
   //비밀번호찾기
   public String findPw(MemberVO vo) throws Exception;
   
   //1:1문의하기 등록
   public void question(QueVO vo, HttpServletRequest request);

   //다른 사람 프로필 조회
   public MemberVO selectOtherProfile(String memberId);
   
   //내가 문의한 리스트 조회
   List<QueVO> selectMyQuestion(String memberId);
}