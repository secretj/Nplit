package com.nplit.service;

import java.util.List;
import java.util.Map;

import com.nplit.vo.BoardVO;
import com.nplit.vo.LikeVO;

public interface LikeService {

	void insertLike(LikeVO vo);

	void deleteLike(LikeVO vo);

	void updateLike(LikeVO vo);

	int getLikeCount(String likeId);

	// ����ũ üũ
	List<Map<String, String>> selectLikeList(Map<String, Object> paramMap);

	int likeCountUpDown(LikeVO vo);

}