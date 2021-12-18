package com.nplit.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nplit.vo.BoardVO;
import com.nplit.vo.LikeVO;
@Mapper
public interface LikeMapper {
   void insertLike(LikeVO vo);
   void deleteLike(LikeVO vo);
   int updateLike(LikeVO vo);
//   public List<LikeVO> getMemberId(LikeVO vo) {
//      return member_id;
//   }
      //전체 수 가져오기
      int getLikeCount(String likeId);
      List<Map<String, String>> selectLikeList(Map<String, Object> paramMap);
      
      int likeCountUpDown(LikeVO vo);


}