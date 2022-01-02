package com.nplit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

//쿼리문 담을 인터페이스
@Mapper
public interface UserMapper {
	MemberVO login(String memberId);

	void keepLogin(MemberVO vo);

	MemberVO checkUserWithSessionKey(String sessionKey);

	void join(MemberVO vo);

	void profileUpdate(MemberVO vo);

	void pwdUpdate(MemberVO vo);

	void memberDelete(MemberVO vo);

	MemberVO LoginInfo(MemberVO vo);

	// ********* 예진 중복검사 ************
	int nicknameChk(MemberVO vo);

	int idChk(MemberVO vo);

	MemberVO LoginInfoEmail(MemberVO vo);

	void question(QueVO vo);

	// 예진 로그인 검사
	int loginChk(MemberVO vo);

	// *************** 진형 ********************

	// 다른 사람 프로필 조회
	MemberVO selectOtherProfile(String memberId);

	// 내가 문의한 리스트 보기
	List<QueVO> selectMyQuestion(String memberId);

}