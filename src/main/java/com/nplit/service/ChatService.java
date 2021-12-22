package com.nplit.service;

import java.util.List;
import java.util.Map;

import com.nplit.vo.ChatMemberListVO;
import com.nplit.vo.ChatMessageVO;
import com.nplit.vo.ChatRoomVO;
import com.nplit.vo.QueVO;

public interface ChatService {
	
	//채팅방 개설
	void craeteRoom(ChatRoomVO vo);
	
	//채팅방 생성시 방장 참가
	void joinRoomMaster(ChatMemberListVO vo);

	//채팅방 참가
	void roomJoin(ChatMemberListVO vo);
	
	//내가 참가한 채팅방 리스트 불러오기
	List<ChatRoomVO> selectMyChatting(String memberId);

	//진형 - 채팅방 정보 조회
	ChatRoomVO selectChattingDetail(int seq);
	
	//메세지 저장
	void insertSendMsg(ChatMessageVO vo);

	//메세지 조회
	 List<ChatMessageVO> selectMessage(int seq);
	
	 //원준 
	 int getListCount(String parId);

	 Object selectMemberList(Map<String, Object> paramMap);
	
	   int chatcheck(ChatMemberListVO cmlvo);
	   
	   
}
