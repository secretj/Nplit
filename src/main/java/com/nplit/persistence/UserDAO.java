package com.nplit.persistence;

import java.sql.Date;

import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

public interface UserDAO {
	 
	public void profileUpdate(MemberVO vo);
	
	public void pwdUpdate(MemberVO vo);
	
    public MemberVO login(MemberVO dto);
     
    // 자동로그인 체크한 경우에 사용자 테이블에 세션과 유효시간을 저장하기 위한 메서드
    public void keepLogin(String memberId, String sessionId, Date next);
     
    // 이전에 로그인한 적이 있는지, 즉 유효시간이 넘지 않은 세션을 가지고 있는지 체크한다.
    public MemberVO checkUserWithSessionKey(String sessionId);
    
    public MemberVO LoginInfo(MemberVO vo);

    //예진  구글 R: 회원 정보 조회 - ID,PW정보에 해당하는 사용자 정보
    public MemberVO readMemberWithIDPW(String memberId,String password) throws Exception;

    //회원가입
	public void join(MemberVO vo);
	 
	//아이디 중복 체크
	public int idChk(MemberVO vo) throws Exception;

	//계정 삭제
    public void memberDelete(MemberVO vo);
    
    //예진 닉네임 중복체크
    int nicknameChk(MemberVO vo) throws Exception;
    
    
    // 비밀번호 변경
    public int updatePw(MemberVO vo) throws Exception;

    //1:1 문의하기
    public void question(QueVO vo);
}