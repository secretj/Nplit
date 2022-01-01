package com.nplit.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nplit.vo.AdminVO;
import com.nplit.vo.AttachVO;
import com.nplit.vo.BoardFileVO;
import com.nplit.vo.BoardVO;
import com.nplit.vo.QueVO;

@Mapper
public interface BoardMapper {
	// CRUD ����� �޼ҵ� ����
	// �� ���
	void insertBoard(BoardVO vo);

	// �� ����
	void updateBoard(BoardVO vo);

	// �� ����
	void deleteBoard(BoardVO vo);

	// �� �� ��ȸ
	BoardVO getBoard(BoardVO vo);

	// �� ��� ��ȸ
	List<BoardVO> getBoardList(BoardVO vo);

	int selectBoardCount(BoardVO vo);

	// ��� ���� ����Ʈ ���
	void insertBoardFileList(BoardFileVO vo);

	// ���ϸ�� ����
	List<AttachVO> getBoardFileList(int seq);

	// ���� ����
	void deleteFile(int seq);

	/********** ���� db ���� ���� ********/

	// ���� �Խñ� ������ �ش� �Խñ��� ÷������ ����
	void deleteFileList(int seq);

	// ä�� ��� ����Ʈ ����
	void deleteBoardChatMemberList(int seq);

	// ä�� �޼��� ����
	void deleteBoardChatMessage(int seq);

	void deleteBoardChatRoom(int seq);

	/*****************/

	// ���ø� ����

	/* ���� ��� ��� */

	// �Խñ� ���
	void register(BoardVO vo);

	// ����Ʈ
	List<BoardVO> registered_list();

	// ���� ī�װ��� ��ϵ� �� ��
	List<Map<String, String>> category_count();

	int category_count_grid();

	/* ���� ���������� */

	// ���� ����� ����Ʈ
	List<BoardVO> registered_mylist(Map<String, Object> paramMap);

	// �� ��ϼ�
	int registered_mylist_count(String id);

	// �Խñ� ����
	void myupdate(BoardVO vo);

	// �Խñ� ����
	void mydelete(int seq);

	// ���������� - ��Ͽ��� �������� �̵�
	BoardVO mydetails(int seq);

	/* ���� ��� ������ */

	// ���� - ��ǰ ������
	Map<String, String> details(int seq);

	// ��ȸ��
	int hitcount(int seq);

	// ����
	List<Map<String, String>> subscribe_list(BoardVO vo);

	// ���ƿ�
	int likeCheck(Map<String, Object> paramMap);

	// �� ��� �� ��� �� �Ϸù�ȣ ȹ��
	int getBoardSeq();

	/* ö�� ÷������ ���ε� */

	// ö�� - ÷������ ���ε�
	void insertAttachFileList(AttachVO file);

	List<Map<String, String>> getsharinglist(Map<String, Object> paramMap);

	// ö�� - ����¡
	int selectRegisterCount(BoardVO vo);

	// ���� ����¡ grid - ��� ī�װ����� total ���� �ٸ��� �����ϱ�
	int selectCategoryRegisterCount(BoardVO vo);

	/* �ε��� */

	// �ֽż� ��ȸ
	List<Map<String, String>> getindexsharinglist(BoardVO vo);

	// �α��
	List<Map<String, String>> getpopularlist(BoardVO vo);

	/*************** ���� ������ ���ǰԽ��� ******************/

	// ���� �Խñ� ���
	List<QueVO> getQuestionList();

	// ������ 1:1 ���� ����
	void QuestionDelete(int seq);

	// ���� �亯 ������
	QueVO QueAnwser(int seq);

	// ���� �亯 ���
	void QueUpdate(QueVO vo);

	// ö�� ���������� - ���� ����� ��� - ����¡ ó��
	List<Map<String, String>> getpagesharinglist(Map<String, Object> paramMap);

	void deleteBoardLike(int seq);

	/*************** ���� ������ �� ��� ******************/

	// �Խñ� ���
	void admin_register(AdminVO vo);

	List<Map<String, String>> admin_getsharinglist(Map<String, Object> paramMap);
}