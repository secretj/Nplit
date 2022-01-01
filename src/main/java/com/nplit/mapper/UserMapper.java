package com.nplit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

//������ ���� �������̽�
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

	// ********* ���� �ߺ��˻� ************
	int nicknameChk(MemberVO vo);

	int idChk(MemberVO vo);

	MemberVO LoginInfoEmail(MemberVO vo);

	void question(QueVO vo);

	// ���� �α��� �˻�
	int loginChk(MemberVO vo);

	// *************** ���� ********************

	// �ٸ� ��� ������ ��ȸ
	MemberVO selectOtherProfile(String memberId);

	// ���� ������ ����Ʈ ����
	List<QueVO> selectMyQuestion(String memberId);

}