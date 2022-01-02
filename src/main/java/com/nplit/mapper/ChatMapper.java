package com.nplit.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nplit.vo.ChatMemberListVO;
import com.nplit.vo.ChatMessageVO;
import com.nplit.vo.ChatRoomVO;
import com.nplit.vo.QueVO;

@Mapper
public interface ChatMapper {

	// 채팅방 개설
	void createRoom(ChatRoomVO vo);

	// 채팅방 생성시 방장 참가
	void joinRoomMaster(ChatMemberListVO vo);

	// 채팅방 참가
	void roomJoin(ChatMemberListVO vo);

	// 내가 참가한 채팅방 리스트 불러오기
	List<ChatRoomVO> selectMyChatting(String memberId);

	// 진형 - 채팅방 정보 조회
	ChatRoomVO selectChattingDetail(int seq);

	// 메세지 전송
	void insertSendMsg(ChatMessageVO vo);

	// 메세지 조회
	List<ChatMessageVO> selectMessage(int seq);

	int getListCount(String parId);

	int chatcheck(ChatMemberListVO cmlvo);

	List<Map<String, String>> selectMemberList(Map<String, Object> paramMap);

}