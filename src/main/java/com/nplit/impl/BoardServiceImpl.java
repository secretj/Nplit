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
 * 업무로직 처리를 담당하는 클래스 ex) 계좌이체 기능 처리할 때 DB입력은 DAO 하게 되는데 DB입력 전 필요한 작업들(계좌암호화, 금액
 * 컴마 추가...) 하는 로직들을 ServiceImpl클래스에서 처리해주면 됨
 */
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;

	public void insertBoard(BoardVO vo) {
		// 객체 생성 시에 필드 변수의 int타입들은 0으로 초기화 됨
//		if(vo.getSeq() == 0) { 
//			throw new IllegalArgumentException("0번 글은 등록할 수 없습니다."); 
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

	/************** 예진 db 연동삭제 *************/

	
	@Override
	// 예진 파일 삭제
	public void deleteFile(int seq) {
		boardMapper.deleteFile(seq);
	}

	//채팅 멤버 리스트 삭제
	@Override
	public void deleteBoardChatMemberList(int seq) {
		boardMapper.deleteBoardChatMemberList(seq);
	}
	// 메세지 내역 삭제
	@Override
	public void deleteBoardChatMessage(int seq) {
		boardMapper.deleteBoardChatMessage(seq);
	}
	
	//채팅 방 삭제
	public void deleteBoardChatRoom(int seq) {
		boardMapper.deleteBoardChatMessage(seq);
	}
	//파일 리스트 삭제
	public void deleteFileList(int seq) {
		boardMapper.deleteFileList(seq);
	}

	// 엔플릿 시작 ******************************************************

	// 게시글 등록
	public void register(BoardVO vo) {
		boardMapper.register(vo);
	}

	public List<BoardVO> registered_list() {
		return boardMapper.registered_list();
	}

	// 내가 등록한 쉐어링 페이지
	public List<BoardVO> registered_mylist(String id, BoardVO vo, Criteria cri) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("vo", vo);
		paramMap.put("cri", cri);
		cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());
		return boardMapper.registered_mylist(paramMap);
	}

	// 내가 참여중인 쉐어링 페이지
//	public List<BoardVO> participate_mylist(String id, BoardVO vo, Criteria cri) {
//		Map<String, Object> paramMap  = new HashMap<String, Object>();
//		paramMap.put("id", id);
//		paramMap.put("vo", vo);
//		paramMap.put("cri", cri);
//		cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());
//		return boardMapper.registered_mylist(paramMap);
//	}

	// 마이페이지 - 등록된 글 수
	public int registered_mylist_count(String id) {
		return boardMapper.registered_mylist_count(id);
	}

	// 게시글 수정
	public void myupdate(BoardVO vo) {
		boardMapper.myupdate(vo);
	}

	// 게시글 삭제
	public void mydelete(int seq) {
		boardMapper.mydelete(seq);
	}

	// 마이페이지 - 디테일
	public BoardVO mydetails(int seq) {
		return boardMapper.mydetails(seq);
	}

	// 하윤 카테고리별 등록된 글 수
	public List<Map<String, String>> category_count() {
		return boardMapper.category_count();
	}

	public int category_count_grid() {
		return boardMapper.category_count_grid();
	}

	/* ********** 원준 ********** */
	// 원준 - 상품 디테일
	public Map<String, String> details(int seq) {
		return boardMapper.details(seq);
	}

	public int hitcount(int seq) {
		return boardMapper.hitcount(seq);
	}

	public int getBoardSeq() {
		return boardMapper.getBoardSeq();
	}

	// 좋아요 기능
	public int likeCheck(Map<String, Object> paramMap) {
		return boardMapper.likeCheck(paramMap);
	}

	// 철우 - 첨부파일 업로드
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
		System.out.println("getBoardList 기능 처리");
		return boardMapper.getsharinglist(paramMap);
	}

	// 하윤 페이징 grid - 쉐어링 카테고리별로 total 개수 다르게 지정하기
	public int selectRegisterCount(BoardVO vo) {
		return boardMapper.selectRegisterCount(vo);
	}

	public int selectCategoryRegisterCount(BoardVO vo) {
		return boardMapper.selectCategoryRegisterCount(vo);
	}

	/* 인덱스 */

	// 최신순
	public List<Map<String, String>> getindexsharinglist(BoardVO vo) {
		return boardMapper.getindexsharinglist(vo);
	}

	// 인기순
	public List<Map<String, String>> getpopularlist(BoardVO vo) {
		return boardMapper.getpopularlist(vo);
	}

	// 구독
	public List<Map<String, String>> subscribe_list(BoardVO vo) {
		return boardMapper.subscribe_list(vo);
	}

	/*************** 진형 관리자 페이지 ******************/

	// 문의 게시글 목록
	@Override
	public List<QueVO> getQuestionList() {
		return boardMapper.getQuestionList();
	}

	// 관리자 1:1 문의 삭제
	public void QuestionDelete(int seq) {
		boardMapper.QuestionDelete(seq);
	}

	// 문의 상세보기
	public QueVO QueAnwser(int seq) {
		return boardMapper.QueAnwser(seq);
	}

	// 문의 답변 작성
	public void QueUpdate(QueVO vo) {
		boardMapper.QueUpdate(vo);
	}

	// 철우 마이페이지 - 내가 등록학 쉐어링 - 페이징 처리
	public List<Map<String, String>> getpagesharinglist(BoardVO vo, Criteria cri) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vo", vo);
		cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());
		paramMap.put("cri", cri);
		System.out.println("getBoardList 기능 처리");
		return boardMapper.getsharinglist(paramMap);
	}

	// 예진 글 삭제시 좋아요 같이 삭제
	@Override
	public void deleteBoardLike(int seq) {
		boardMapper.deleteBoardLike(seq);

	}

	/*************** 하윤 관리자 글 등록 ******************/

	// 하윤 관리자 게시글 등록
	public void admin_register(AdminVO vo) {
		boardMapper.admin_register(vo);
	}

	// 내가 등록한 쉐어링 페이지

	/*
	 * public List<BoardVO> admin_register_mylist(String id, BoardVO vo) {
	 * Map<String, Object> paramMap = new HashMap<String, Object>();
	 * paramMap.put("id", id); paramMap.put("vo", vo); return
	 * boardMapper.registered_mylist(paramMap); }
	 */

	public List<Map<String, String>> admin_getsharinglist(AdminVO vo, Criteria cri) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vo", vo);
		System.out.println("getBoardList 기능 처리");
		return boardMapper.admin_getsharinglist(paramMap);
	}

}
