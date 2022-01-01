package com.nplit.persistence;

import java.sql.Date;

import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

public interface UserDAO {

	public void profileUpdate(MemberVO vo);

	public void pwdUpdate(MemberVO vo);

	public MemberVO login(MemberVO dto);

	// �ڵ��α��� üũ�� ��쿡 ����� ���̺� ���ǰ� ��ȿ�ð��� �����ϱ� ���� �޼���
	public void keepLogin(String memberId, String sessionId, Date next);

	// ������ �α����� ���� �ִ���, �� ��ȿ�ð��� ���� ���� ������ ������ �ִ��� üũ�Ѵ�.
	public MemberVO checkUserWithSessionKey(String sessionId);

	public MemberVO LoginInfo(MemberVO vo);

	// ���� ���� R: ȸ�� ���� ��ȸ - ID,PW������ �ش��ϴ� ����� ����
	public MemberVO readMemberWithIDPW(String memberId, String password) throws Exception;

	// ȸ������
	public void join(MemberVO vo);

	// ���̵� �ߺ� üũ
	public int idChk(MemberVO vo) throws Exception;

	// ���� ����
	public void memberDelete(MemberVO vo);

	// ���� �г��� �ߺ�üũ
	int nicknameChk(MemberVO vo) throws Exception;

	// ��й�ȣ ����
	public int updatePw(MemberVO vo) throws Exception;

	// 1:1 �����ϱ�
	public void question(QueVO vo);
}