package com.nplit.service;

import java.util.List;
import java.util.Map;

import com.nplit.vo.ChatMemberListVO;
import com.nplit.vo.ChatMessageVO;
import com.nplit.vo.ChatRoomVO;
import com.nplit.vo.QueVO;

public interface ChatService {

	// ä�ù� ����
	void craeteRoom(ChatRoomVO vo);

	// ä�ù� ������ ���� ����
	void joinRoomMaster(ChatMemberListVO vo);

	// ä�ù� ����
	void roomJoin(ChatMemberListVO vo);

	// ���� ������ ä�ù� ����Ʈ �ҷ�����
	List<ChatRoomVO> selectMyChatting(String memberId);

	// ���� - ä�ù� ���� ��ȸ
	ChatRoomVO selectChattingDetail(int seq);

	// �޼��� ����
	void insertSendMsg(ChatMessageVO vo);

	// �޼��� ��ȸ
	List<ChatMessageVO> selectMessage(int seq);

	// ����
	int getListCount(String parId);

	Object selectMemberList(Map<String, Object> paramMap);

	int chatcheck(ChatMemberListVO cmlvo);

}
