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

	// ä�ù� ����
	@Override
	public void craeteRoom(ChatRoomVO vo) {
		chatMapper.createRoom(vo);
	}

	// ä�ù� ������ ���� ����
	@Override
	public void joinRoomMaster(ChatMemberListVO vo) {
		chatMapper.joinRoomMaster(vo);
	}

	// ä�ù� ����
	@Override
	public void roomJoin(ChatMemberListVO vo) {
		chatMapper.roomJoin(vo);
	}

	// ���� ������ ä�� ����Ʈ�̾ƿ���
	public List<ChatRoomVO> selectMyChatting(String memberId) {

		List<ChatRoomVO> chatlist = chatMapper.selectMyChatting(memberId);
		return chatlist;
	}

	public int chatcheck(ChatMemberListVO cmlvo) {
		return chatMapper.chatcheck(cmlvo);

	}

	// ���� - ä�ù� ���� ��ȸ
	public ChatRoomVO selectChattingDetail(int seq) {
		return chatMapper.selectChattingDetail(seq);
	}

	// �޼��� ����
	@Override
	public void insertSendMsg(ChatMessageVO vo) {
		chatMapper.insertSendMsg(vo);
	}

	// ���� - �޼��� ��ȸ
	public List<ChatMessageVO> selectMessage(int seq) {
		List<ChatMessageVO> msglist = chatMapper.selectMessage(seq);
		return msglist;
	}

	// ��ü �� ��������
	public int getListCount(String parId) {
		return chatMapper.getListCount(parId);
	}

	// ��������
	public List<Map<String, String>> selectMemberList(Map<String, Object> paramMap) {
		return chatMapper.selectMemberList(paramMap);
	}

}
