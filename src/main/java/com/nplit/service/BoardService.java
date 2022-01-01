package com.nplit.service;

import java.util.List;
import java.util.Map;

import com.nplit.vo.AdminVO;
import com.nplit.vo.AttachVO;
import com.nplit.vo.BoardFileVO;
import com.nplit.vo.BoardVO;
import com.nplit.vo.Criteria;
import com.nplit.vo.QueVO;

public interface BoardService {
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

	// ��� ���� ����Ʈ ���
	void insertBoardFileList(List<BoardFileVO> fileList);

	// ���ϸ�� ����
	List<AttachVO> getBoardFileList(int seq);

	// ���ϻ���
	void deleteFile(int seq);

	// ***********���� db ���� ���� **********/
	// �Խñ� ���� �� �ش� �Խñ��� ÷������ ��� ����
	void deleteFileList(int seq);

	void deleteBoardChatMemberList(int seq);

	void deleteBoardChatMessage(int seq);

	void deleteBoardChatRoom(int seq);

	// ���ø� ����

	// ************* ���� ************************

	// �Խñ� ���
	void register(BoardVO vo);

	List<BoardVO> registered_list();

	// ���� ����� ����Ʈ ��ȸ
	List<BoardVO> registered_mylist(String id, BoardVO vo, Criteria cri);

	// �� ��ϼ�
	int registered_mylist_count(String id);

	// �Խñ� ����
	void myupdate(BoardVO vo);

	// �����Խñ� ����
	void mydelete(int seq);

	// �� ��� �� ��� �� �Ϸù�ȣ ȹ��
	int getBoardSeq();

	// ī�װ� �� ����
	List<Map<String, String>> category_count();

	int category_count_grid();

	// ************* ���� ************************

	List<Map<String, String>> subscribe_list(BoardVO vo);

	// ���� - ��ǰ ������
	Map<String, String> details(int seq);

	int hitcount(int seq);

	BoardVO mydetails(int seq);

	// ���ƿ� ���
	int likeCheck(Map<String, Object> paramMap);

	// ************* ö�� ************************

	// ö�� - ÷������ ���ε�
	void insertAttachFileList(List<AttachVO> fileList);

	List<Map<String, String>> getsharinglist(BoardVO vo, Criteria cri);

	// ö�� - ����¡

	int selectRegisterCount(BoardVO vo);

	// ���� ����¡ grid - ��� ī�װ����� total ���� �ٸ��� �����ϱ�
	int selectCategoryRegisterCount(BoardVO vo);

	List<Map<String, String>> getindexsharinglist(BoardVO vo);

	List<Map<String, String>> getpopularlist(BoardVO vo);

	// ö�� ���������� - ���� ����� ��� - ����¡ ó��
	List<Map<String, String>> getpagesharinglist(BoardVO vo, Criteria cri);

	/*************** ���� ������ ������ ******************/
	// ���� �Խñ� ���
	List<QueVO> getQuestionList();

	// ������ ���ǻ��� -����
	void QuestionDelete(int seq);

	// ���� �亯 ������
	QueVO QueAnwser(int seq);

	// ���� �亯 �ۼ�
	void QueUpdate(QueVO vo);

	// ����
	void deleteBoardLike(int seq);

	/*************** ���� ������ �� ��� ******************/
	// ���� ������ ������ - �� ���
	void admin_register(AdminVO vo);

	List<Map<String, String>> admin_getsharinglist(AdminVO vo, Criteria cri);

}
