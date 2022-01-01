package com.nplit.service;

import java.util.List;
import java.util.Map;

import com.nplit.vo.BoardVO;
import com.nplit.vo.Criteria;
import com.nplit.vo.MemberListVO;

public interface MemberListService {

	void inShare(MemberListVO vo);

	void outShare(MemberListVO vo);

	void updateMemberList(MemberListVO vo);

	int getListCount(String parId);

	// ����ũ üũ
	List<Map<String, String>> selectMemberList(Map<String, Object> paramMap);
}
