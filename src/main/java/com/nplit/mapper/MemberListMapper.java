package com.nplit.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nplit.vo.Criteria;
import com.nplit.vo.MemberListVO;
@Mapper
public interface MemberListMapper {
   void inShare(MemberListVO vo);
   void outShare(MemberListVO vo);
   int updateMemberList(MemberListVO vo);
//   public List<LikeVO> getMemberId(LikeVO vo) {
//      return member_id;
//   }
      //전체 수 가져오기
      int getListCount(String parId);
      List<Map<String, String>> selectMemberList(Map<String, Object> paramMap);
      

}