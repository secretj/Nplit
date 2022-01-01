package com.nplit.service;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

public interface UserService {
	// �α���
	public MemberVO login(MemberVO dto);

	// �ڵ��α��� üũ�� ��쿡 ����� ���̺� ���ǰ� ��ȿ�ð��� �����ϱ� ���� �޼���
	public void keepLogin(String member_id, String sessionId, Date next);

	// ������ �α����� ���� �ִ���, �� ��ȿ�ð��� ���� ���� ������ ������ �ִ��� üũ�Ѵ�.
	public MemberVO checkUserWithSessionKey(String sessionId);

	// ȸ������
	void join(MemberVO vo);

	// ������������
	void profileUpdate(MemberVO vo);

	// ��й�ȣ ����
	void pwdUpdate(MemberVO vo);

	// ȸ��Ż��
	void memberDelete(MemberVO vo);

	// �α��� ȸ�� ����
	MemberVO LoginInfo(MemberVO vo);

	// ����ȸ������
	public void joinMemberByGoogle(MemberVO vo);

	// ���۷α���
	public MemberVO loginMemberByGoogle(MemberVO vo);

	// ********* ���� �ߺ��˻� ************
	// ���̵� �ߺ�Ȯ�� üũ
	public int idChk(MemberVO vo) throws Exception;

	// ���� �г��� �ߺ�üũ
	public int nicknameChk(MemberVO vo) throws Exception;

	// ���� �α��� �˻�
	public int loginChk(MemberVO vo) throws Exception;

	// �̸��Ϲ߼�
	public void sendEmail(MemberVO vo, String div) throws Exception;

	// ��й�ȣã��
	public String findPw(MemberVO vo) throws Exception;

	// 1:1�����ϱ� ���
	public void question(QueVO vo, HttpServletRequest request);

	// �ٸ� ��� ������ ��ȸ
	public MemberVO selectOtherProfile(String memberId);

	// ���� ������ ����Ʈ ��ȸ
	List<QueVO> selectMyQuestion(String memberId);
}