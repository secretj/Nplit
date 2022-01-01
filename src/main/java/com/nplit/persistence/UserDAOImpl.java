package com.nplit.persistence;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

@Repository
public class UserDAOImpl implements UserDAO {
	@Inject
	SqlSession sqlSession;

	/**
	 * login�� �����ϸ�, ���� ������ ��� �ִ� UserVO ��ü�� ��ȯ�Ѵ�.
	 */
	@Override
	public MemberVO login(MemberVO dto) {
		// Mapper�� namespace��.id : �ڽſ��� �°� �ۼ��ؼ� �ִ´�.
		return sqlSession.selectOne("com.nplit.mapper.UserMapper.login", dto);
	}

	// �������� ����
	@Override
	public void profileUpdate(MemberVO vo) {
		sqlSession.update("com.nplit.mapper.UserMapper.profileUpdate", vo);
	}

	// ��й�ȣ ����
	@Override
	public void pwdUpdate(MemberVO vo) {
		sqlSession.update("com.nplit.mapper.UserMapper.pwdUpdate", vo);
	}

	// ȸ��Ż��
	@Override
	public void memberDelete(MemberVO vo) {
		sqlSession.delete("com.nplit.mapper.UserMapper.memberDelete", vo);
	}

	// �ڵ��α��� üũ�� ��쿡 ����� ���̺� ���ǰ� ��ȿ�ð��� �����ϱ� ���� �޼���
	public void keepLogin(String memberId, String sessionId, Date next) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("sessionId", sessionId);
		map.put("next", next);

		// Mapper.xml�� �����͸� ������ �� �� ��ü�ۿ� ���� �������� map���� ��� ������
		// ��... ������ ����
		// Mapper.xml �ȿ��� #{} �� �ȿ� ������ �̸��̶� ���ƾ���.. �ڵ����� ���ε� �� �ֵ���
		// �Ʒ��� ����Ǹ鼭, ����� ���̺� ����id�� ��ȿ�ð��� �����
		sqlSession.update("com.nplit.mapper.UserMapper.keepLogin", map);
	}

	// ������ �α����� ���� �ִ���, �� ��ȿ�ð��� ���� ���� ������ ������ �ִ��� üũ�Ѵ�.
	public MemberVO checkUserWithSessionKey(String sessionId) {
		// ��ȿ�ð��� �����ְ�(>now()) ���޹��� ���� id�� ��ġ�ϴ� ����� ������ ������.
		return sqlSession.selectOne("com.nplit.mapper.UserMapper.checkUserWithSessionKey", sessionId);

	}

	@Override
	public MemberVO LoginInfo(MemberVO vo) {
		return sqlSession.selectOne("com.nplit.mapper.UserMapper.LoginInfo", vo);
	}

	// ����
	// ȸ�� ���� ��ȸ - ID,PW������ �ش��ϴ� ����� ����
	@Override
	public MemberVO readMemberWithIDPW(String member_id, String password) throws Exception {
		// �׽�Ʈ(��Ʈ�ѷ�) ȣ�� -> ������ ���� -> DB���̵�

		// String���ڸ� 2���� ������ �� ���⶧���� �Ķ���� �ΰ��� ��ü�� Map�� �־ ������ �ѱ��
		// return sqlSession.selectOne(namespace+".readMemberWithIDPW", userid, userpw);

		// DB�� ������ �����ϱ� ���ؼ��� sqlSeesion ��ü Ȱ��
		// * 1�� �̻��� ������ �����Ҷ��� ��ü ������ ����
		// * ��ü(VO) �ȿ� ������ �ȵǴ� ������ ��� Map�� ���
		// Map�� key-value���� : �̶� key���� sql������ #{������} �̸��� ���ƾ���

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", member_id);
		paramMap.put("password", password);

		System.out.println("readMemberWithIDPW ���� ���� ���� " + paramMap.get(member_id));
		// return sqlSession.selectOne("com.nplit.mapper.UserMapper.readMemberWithIDPW",
		// paramMap);
		return sqlSession.selectOne("com.nplit.mapper.UserMapper.login", paramMap);
	}

	// ���� ������ �߰�
	@Override
	public void join(MemberVO vo) {
		sqlSession.insert("com.nplit.mapper.UserMapper.join", vo);
	}

	// ���� ���̵� �ߺ�üũ
	@Override
	public int idChk(MemberVO vo) throws Exception {
		int result = sqlSession.selectOne("com.nplit.mapper.UserMapper.idChk", vo);
		return result;
	}

	// ���� �г��� �ߺ�üũ
	@Override
	public int nicknameChk(MemberVO vo) throws Exception {
		int result = sqlSession.selectOne("com.nplit.mapper.UserMapper.nicknameChk", vo.getNickname());
		System.out.println("UserDAOImpl ���� ��ȯ�� : " + result);
		return result;

	}

	// ��й�ȣ����
	@Override
	public int updatePw(MemberVO vo) throws Exception {
		return sqlSession.update("com.nplit.mapper.UserMapper.updatePw", vo);
	}

	// 1:1�����ϱ�
	@Override
	public void question(QueVO vo) {
		sqlSession.insert("com.nplit.mapper.UserMapper.question", vo);
	}

}