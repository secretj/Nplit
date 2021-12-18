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
	//CRUD 기능의 메소드 구현
	//글 등록
	void insertBoard(BoardVO vo);
	
	//글 수정
	void updateBoard(BoardVO vo);
	
	//글 삭제
	void deleteBoard(BoardVO vo);
	
	//글 상세 조회
	BoardVO getBoard(BoardVO vo);
	
	//글 목록 조회
	List<BoardVO> getBoardList(BoardVO vo);
	
	int selectBoardCount(BoardVO vo);

	
	//디비에 파일 리스트 등록
	void insertBoardFileList(BoardFileVO vo);
	
	//파일목록 리턴
	List<AttachVO> getBoardFileList(int seq);
	
	   
  //파일 삭제
  void deleteFile(int seq);
	
  /**********예진 db 연동 삭제********/
  
  
	//예진 게시글 삭제시 해당 게시글의 첨부파일 삭제
	void deleteFileList(int seq);
	
	// 채팅 멤버 리스트 삭제
	void deleteBoardChatMemberList(int seq);
	
	// 채팅 메세지 삭제
	void deleteBoardChatMessage(int seq);
	
	void deleteBoardChatRoom(int seq);

	
	/*****************/
	
	//엔플릿 시작
	
	/* 하윤 쉐어링 등록  */
	
	//게시글 등록
	void register(BoardVO vo);
	
	//리스트
	List<BoardVO> registered_list();
	
	// 하윤 카테고리별 등록된 글 수
	List<Map<String, String>> category_count();
	
	int category_count_grid();
	
	/* 하윤 마이페이지   */
	
	//내가 등록한 리스트
	List<BoardVO> registered_mylist(Map<String, Object> paramMap);
	
	//내가 참여한 리스트
//		List<BoardVO> participate_mylist(Map<String, Object> paramMap);
			
	
	//총 등록수
	int registered_mylist_count(String id);
	
	
	//게시글 수정
	void myupdate(BoardVO vo);
			
	//게시글 삭제
	void mydelete(int seq);

	//마이페이지 - 목록에서 상세페이지 이동
	BoardVO mydetails(int seq);

	
	/* 원준 쉐어링 디테일  */
	
	// 원준 - 상품 디테일
	Map<String, String> details(int seq);
	
	//조회수
	int hitcount(int seq);
	
	//구독
	List<Map<String, String>> subscribe_list(BoardVO vo);
	
	//좋아요
    int likeCheck(Map<String, Object> paramMap);
	
	//글 등록 전 등록 될 일련번호 획득
	int getBoardSeq();
	
	
	/* 철우 첨부파일 업로드  */
	
	// 철우 - 첨부파일 업로드
    void insertAttachFileList(AttachVO file);
    
    List<Map<String, String>> getsharinglist(Map<String, Object> paramMap);
       
   // 철우 - 페이징
   int selectRegisterCount(BoardVO vo);
   
   // 하윤 페이징 grid - 쉐어링 카테고리별로 total 개수 다르게 지정하기
   int selectCategoryRegisterCount(BoardVO vo);
   
   /* 인덱스  */
   
   //최신순 조회
   List<Map<String, String>> getindexsharinglist(BoardVO vo);
   
   //인기순
   List<Map<String, String>> getpopularlist(BoardVO vo);
   
   
   /*************** 진형 관리자 문의게시판 ******************/
   
   //문의 게시글 목록
   List<QueVO> getQuestionList();
   
   //관리자 1:1 문의 삭제
  void QuestionDelete(int seq);
  
  //문의 답변 페이지
  QueVO QueAnwser(int seq);
  
  //문의 답변 등록
  void QueUpdate(QueVO vo);
  
  //철우 마이페이지  - 내가 등록학 쉐어링 - 페이징 처리
  List<Map<String, String>> getpagesharinglist(Map<String, Object> paramMap);

	void deleteBoardLike(int seq);
	
	/*************** 하윤 관리자 글 등록 ******************/
	
	//게시글 등록
	void admin_register(AdminVO vo);
	
	//리스트
//	List<BoardVO> admin_register_list();
	
	List<Map<String, String>> admin_getsharinglist(Map<String, Object> paramMap);
}