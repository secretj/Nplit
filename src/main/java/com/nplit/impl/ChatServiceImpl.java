package com.nplit.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nplit.mapper.ChatMapper;
import com.nplit.service.ChatService;
import com.nplit.vo.ChatMemberListVO;
import com.nplit.vo.ChatMessageVO;
import com.nplit.vo.ChatRoomVO;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatMapper chatMapper;

	// 채팅방 개설
	@Override
	public void craeteRoom(ChatRoomVO vo) {
		chatMapper.createRoom(vo);
	}

	// 채팅방 생성시 방장 참가
	@Override
	public void joinRoomMaster(ChatMemberListVO vo) {
		chatMapper.joinRoomMaster(vo);
	}

	// 채팅방 참가
	@Override
	public void roomJoin(ChatMemberListVO vo) {
		chatMapper.roomJoin(vo);
	}

	// 내가 참가한 채팅 리스트뽑아오기
	public List<ChatRoomVO> selectMyChatting(String memberId) {

		List<ChatRoomVO> chatlist = chatMapper.selectMyChatting(memberId);
		return chatlist;
	}

	public int chatcheck(ChatMemberListVO cmlvo) {
		return chatMapper.chatcheck(cmlvo);

	}

	// 진형 - 채팅방 정보 조회
	public ChatRoomVO selectChattingDetail(int seq) {
		return chatMapper.selectChattingDetail(seq);
	}

	// 메세지 전송
	@Override
	public void insertSendMsg(ChatMessageVO vo) {
		chatMapper.insertSendMsg(vo);
	}

	// 진형 - 메세지 조회
	public List<ChatMessageVO> selectMessage(int seq) {
		List<ChatMessageVO> msglist = chatMapper.selectMessage(seq);
		return msglist;
	}

	// 전체 수 가져오기
	public int getListCount(String parId) {
		return chatMapper.getListCount(parId);
	}

	// 내가찜한
	public List<Map<String, String>> selectMemberList(Map<String, Object> paramMap) {
		return chatMapper.selectMemberList(paramMap);
	}

}
