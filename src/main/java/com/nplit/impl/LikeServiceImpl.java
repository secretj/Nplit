package com.nplit.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nplit.mapper.LikeMapper;
import com.nplit.service.LikeService;
import com.nplit.vo.BoardVO;
import com.nplit.vo.LikeVO;

@Service("likeService")
public class LikeServiceImpl implements LikeService {

	@Autowired
	private LikeMapper likeMapper;

	public void insertLike(LikeVO vo) {
		likeMapper.insertLike(vo);

	}

	public void deleteLike(LikeVO vo) {
		likeMapper.deleteLike(vo);

	}

	public void updateLike(LikeVO vo) {
		likeMapper.updateLike(vo);
	}

	// ��ü �� ��������
	public int getLikeCount(String likeId) {
		return likeMapper.getLikeCount(likeId);

	}

	// �������� ���
	public List<Map<String, String>> selectLikeList(Map<String, Object> paramMap) {
		return likeMapper.selectLikeList(paramMap);
	}

	public int likeCountUpDown(LikeVO vo) {
		return likeMapper.likeCountUpDown(vo);
	}

}