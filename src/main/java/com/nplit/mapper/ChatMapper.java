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

	// ä�ù� ����
	void createRoom(ChatRoomVO vo);

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

	int getListCount(String parId);

	int chatcheck(ChatMemberListVO cmlvo);

	List<Map<String, String>> selectMemberList(Map<String, Object> paramMap);

}
