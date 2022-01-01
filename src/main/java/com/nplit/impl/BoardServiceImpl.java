package com.nplit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nplit.mapper.BoardMapper;
import com.nplit.service.BoardService;
import com.nplit.vo.AdminVO;
import com.nplit.vo.AttachVO;
import com.nplit.vo.BoardFileVO;
import com.nplit.vo.BoardVO;
import com.nplit.vo.Criteria;
import com.nplit.vo.QueVO;

@Service("boardService")
/*
 * �������� ó���� ����ϴ� Ŭ���� ex) ������ü ��� ó���� �� DB�Է��� DAO �ϰ� �Ǵµ�
 * DB�Է� �� �ʿ��� �۾���(���¾�ȣȭ, �ݾ� �ĸ� �߰�...) �ϴ� ��������
 * ServiceImplŬ�������� ó�����ָ� ��
 */
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;

	public void insertBoard(BoardVO vo) {
		// ��ü ���� �ÿ� �ʵ� ������ intŸ�Ե��� 0���� �ʱ�ȭ ��
//		if(vo.getSeq() == 0) { 
//			throw new IllegalArgumentException("0�� ���� ����� �� �����ϴ�."); 
//		}
		boardMapper.insertBoard(vo);
		/* boardDAO.insertBoard(vo); */
	}

	public void updateBoard(BoardVO vo) {
		boardMapper.updateBoard(vo);
	}

	public void deleteBoard(BoardVO vo) {
		boardMapper.deleteBoard(vo);
	}

	public BoardVO getBoard(BoardVO vo) {
		return boardMapper.getBoard(vo);
	}

	public List<BoardVO> getBoardList(BoardVO vo) {
		return boardMapper.getBoardList(vo);
	}

	public void insertBoardFileList(List<BoardFileVO> fileList) {
		for (BoardFileVO vo : fileList) {
			boardMapper.insertBoardFileList(vo);
		}
	}

	public List<AttachVO> getBoardFileList(int seq) {
		return boardMapper.getBoardFileList(seq);
	}

	/************** ���� db �������� *************/

	@Override
	// ���� ���� ����
	public void deleteFile(int seq) {
		boardMapper.deleteFile(seq);
	}

	// ä�� ��� ����Ʈ ����
	@Override
	public void deleteBoardChatMemberList(int seq) {
		boardMapper.deleteBoardChatMemberList(seq);
	}

	// �޼��� ���� ����
	@Override
	public void deleteBoardChatMessage(int seq) {
		boardMapper.deleteBoardChatMessage(seq);
	}

	// ä�� �� ����
	public void deleteBoardChatRoom(int seq) {
		boardMapper.deleteBoardChatMessage(seq);
	}

	// ���� ����Ʈ ����
	public void deleteFileList(int seq) {
		boardMapper.deleteFileList(seq);
	}

	// ���ø� ���� ******************************************************

	// �Խñ� ���
	public void register(BoardVO vo) {
		boardMapper.register(vo);
	}

	public List<BoardVO> registered_list() {
		return boardMapper.registered_list();
	}

	// ���� ����� ��� ������
	public List<BoardVO> registered_mylist(String id, BoardVO vo, Criteria cri) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("vo", vo);
		paramMap.put("cri", cri);
		cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());
		return boardMapper.registered_mylist(paramMap);
	}

	// ���������� - ��ϵ� �� ��
	public int registered_mylist_count(String id) {
		return boardMapper.registered_mylist_count(id);
	}

	// �Խñ� ����
	public void myupdate(BoardVO vo) {
		boardMapper.myupdate(vo);
	}

	// �Խñ� ����
	public void mydelete(int seq) {
		boardMapper.mydelete(seq);
	}

	// ���������� - ������
	public BoardVO mydetails(int seq) {
		return boardMapper.mydetails(seq);
	}

	// ���� ī�װ��� ��ϵ� �� ��
	public List<Map<String, String>> category_count() {
		return boardMapper.category_count();
	}

	public int category_count_grid() {
		return boardMapper.category_count_grid();
	}

	/* ********** ���� ********** */
	// ���� - ��ǰ ������
	public Map<String, String> details(int seq) {
		return boardMapper.details(seq);
	}

	public int hitcount(int seq) {
		return boardMapper.hitcount(seq);
	}

	public int getBoardSeq() {
		return boardMapper.getBoardSeq();
	}

	// ���ƿ� ���
	public int likeCheck(Map<String, Object> paramMap) {
		return boardMapper.likeCheck(paramMap);
	}

	// ö�� - ÷������ ���ε�
	public void insertAttachFileList(List<AttachVO> fileList) {
		for (AttachVO file : fileList) {
			boardMapper.insertAttachFileList(file);
		}
	}

	public List<Map<String, String>> getsharinglist(BoardVO vo, Criteria cri) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vo", vo);
		System.out
				.println("vo.getSearchAddress()==============================================" + vo.getSearchAddress());
		cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());
		paramMap.put("cri", cri);
		System.out.println("getBoardList ��� ó��");
		return boardMapper.getsharinglist(paramMap);
	}

	// ���� ����¡ grid - ��� ī�װ����� total ���� �ٸ��� �����ϱ�
	public int selectRegisterCount(BoardVO vo) {
		return boardMapper.selectRegisterCount(vo);
	}

	public int selectCategoryRegisterCount(BoardVO vo) {
		return boardMapper.selectCategoryRegisterCount(vo);
	}

	/* �ε��� */

	// �ֽż�
	public List<Map<String, String>> getindexsharinglist(BoardVO vo) {
		return boardMapper.getindexsharinglist(vo);
	}

	// �α��
	public List<Map<String, String>> getpopularlist(BoardVO vo) {
		return boardMapper.getpopularlist(vo);
	}

	// ����
	public List<Map<String, String>> subscribe_list(BoardVO vo) {
		return boardMapper.subscribe_list(vo);
	}

	/*************** ���� ������ ������ ******************/

	// ���� �Խñ� ���
	@Override
	public List<QueVO> getQuestionList() {
		return boardMapper.getQuestionList();
	}

	// ������ 1:1 ���� ����
	public void QuestionDelete(int seq) {
		boardMapper.QuestionDelete(seq);
	}

	// ���� �󼼺���
	public QueVO QueAnwser(int seq) {
		return boardMapper.QueAnwser(seq);
	}

	// ���� �亯 �ۼ�
	public void QueUpdate(QueVO vo) {
		boardMapper.QueUpdate(vo);
	}

	// ö�� ���������� - ���� ����� ��� - ����¡ ó��
	public List<Map<String, String>> getpagesharinglist(BoardVO vo, Criteria cri) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vo", vo);
		cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());
		paramMap.put("cri", cri);
		System.out.println("getBoardList ��� ó��");
		return boardMapper.getsharinglist(paramMap);
	}

	// ���� �� ������ ���ƿ� ���� ����
	@Override
	public void deleteBoardLike(int seq) {
		boardMapper.deleteBoardLike(seq);

	}

	/*************** ���� ������ �� ��� ******************/

	// ���� ������ �Խñ� ���
	public void admin_register(AdminVO vo) {
		boardMapper.admin_register(vo);
	}

	public List<Map<String, String>> admin_getsharinglist(AdminVO vo, Criteria cri) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vo", vo);
		System.out.println("getBoardList ��� ó��");
		return boardMapper.admin_getsharinglist(paramMap);
	}

}
