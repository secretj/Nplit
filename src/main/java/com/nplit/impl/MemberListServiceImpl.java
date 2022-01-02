
package com.nplit.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nplit.mapper.MemberListMapper;
import com.nplit.service.MemberListService;
import com.nplit.vo.MemberListVO;

@Service("mlService")
public class MemberListServiceImpl implements MemberListService {

	@Autowired
	private MemberListMapper mlMapper;

	public void inShare(MemberListVO vo) {
		mlMapper.inShare(vo);

	}

	public void outShare(MemberListVO vo) {
		mlMapper.outShare(vo);

	}

	public void updateMemberList(MemberListVO vo) {
		mlMapper.updateMemberList(vo);
	}

//전체 수 가져오기
	public int getListCount(String parId) {
		return mlMapper.getListCount(parId);
	}

// 내가찜한 
	public List<Map<String, String>> selectMemberList(Map<String, Object> paramMap) {
		return mlMapper.selectMemberList(paramMap);
	}

}