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
	 * login에 성공하면, 유저 정보를 담고 있는 UserVO 객체를 반환한다.
	 */
	@Override
	public MemberVO login(MemberVO dto) {
		// Mapper의 namespace명.id : 자신에게 맞게 작성해서 넣는다.
		return sqlSession.selectOne("com.nplit.mapper.UserMapper.login", dto);
	}

	// 개인정보 수정
	@Override
	public void profileUpdate(MemberVO vo) {
		sqlSession.update("com.nplit.mapper.UserMapper.profileUpdate", vo);
	}

	// 비밀번호 수정
	@Override
	public void pwdUpdate(MemberVO vo) {
		sqlSession.update("com.nplit.mapper.UserMapper.pwdUpdate", vo);
	}

	// 회원탈퇴
	@Override
	public void memberDelete(MemberVO vo) {
		sqlSession.delete("com.nplit.mapper.UserMapper.memberDelete", vo);
	}

	// 자동로그인 체크한 경우에 사용자 테이블에 세션과 유효시간을 저장하기 위한 메서드
	public void keepLogin(String memberId, String sessionId, Date next) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("sessionId", sessionId);
		map.put("next", next);

		// Mapper.xml로 데이터를 전달할 때 한 객체밖에 전달 못함으로 map으로 묶어서 보내줌 단... 주의할 점은
		// Mapper.xml 안에서 #{} 이 안에 지정한 이름이랑 같아야함.. 자동으로 매핑될 수 있도록
		// 아래가 수행되면서, 사용자 테이블에 세션id와 유효시간이 저장됨
		sqlSession.update("com.nplit.mapper.UserMapper.keepLogin", map);
	}

	// 이전에 로그인한 적이 있는지, 즉 유효시간이 넘지 않은 세션을 가지고 있는지 체크한다.
	public MemberVO checkUserWithSessionKey(String sessionId) {
		// 유효시간이 남아있고(>now()) 전달받은 세션 id와 일치하는 사용자 정보를 꺼낸다.
		return sqlSession.selectOne("com.nplit.mapper.UserMapper.checkUserWithSessionKey", sessionId);

	}

	@Override
	public MemberVO LoginInfo(MemberVO vo) {
		return sqlSession.selectOne("com.nplit.mapper.UserMapper.LoginInfo", vo);
	}

	// 구글
	// 회원 정보 조회 - ID,PW정보에 해당하는 사용자 정보
	@Override
	public MemberVO readMemberWithIDPW(String member_id, String password) throws Exception {
		// 테스트(컨트롤러) 호출 -> 정보를 저장 -> DB로이동

		// String인자를 2개를 가져갈 수 없기때문에 파라미터 두개를 객체인 Map에 넣어서 가지고 넘긴다
		// return sqlSession.selectOne(namespace+".readMemberWithIDPW", userid, userpw);

		// DB로 정보를 전달하기 위해서는 sqlSeesion 객체 활용
		// * 1개 이상의 정보를 전달할때는 객체 단위로 전달
		// * 객체(VO) 안에 저장이 안되는 정보의 경우 Map을 사용
		// Map은 key-value형태 : 이때 key값은 sql구문의 #{ㅇㅇㅇ} 이름과 같아야함

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", member_id);
		paramMap.put("password", password);

		return sqlSession.selectOne("com.nplit.mapper.UserMapper.login", paramMap);
	}

	// 구글 때문에 추가
	@Override
	public void join(MemberVO vo) {
		sqlSession.insert("com.nplit.mapper.UserMapper.join", vo);
	}

	// 예진 아이디 중복체크
	@Override
	public int idChk(MemberVO vo) throws Exception {
		int result = sqlSession.selectOne("com.nplit.mapper.UserMapper.idChk", vo);
		return result;
	}

	// 예진 닉네임 중복체크
	@Override
	public int nicknameChk(MemberVO vo) throws Exception {
		int result = sqlSession.selectOne("com.nplit.mapper.UserMapper.nicknameChk", vo.getNickname());
		System.out.println("UserDAOImpl 에서 반환값 : " + result);
		return result;

	}

	// 비밀번호변경
	@Override
	public int updatePw(MemberVO vo) throws Exception {
		return sqlSession.update("com.nplit.mapper.UserMapper.updatePw", vo);
	}

	// 1:1문의하기
	@Override
	public void question(QueVO vo) {
		sqlSession.insert("com.nplit.mapper.UserMapper.question", vo);
	}

}