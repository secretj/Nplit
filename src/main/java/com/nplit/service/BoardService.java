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



	//디비에 파일 리스트 등록
	void insertBoardFileList(List<BoardFileVO> fileList);

	//파일목록 리턴
	List<AttachVO> getBoardFileList(int seq);

   //파일삭제
   void deleteFile(int seq);

   //***********예진 db 연동 삭제 **********/
	//게시글 삭제 시 해당 게시글의 첨부파일 모두 삭제
	void deleteFileList(int seq);
void deleteBoardChatMemberList(int seq);  
void deleteBoardChatMessage(int seq);  
void deleteBoardChatRoom(int seq);  
	
	
	//엔플릿 시작

	// ************* 하윤 ************************ 
	
	//게시글 등록
	void register(BoardVO vo);

	List<BoardVO> registered_list();

	//내가 등록한 리스트 조회
	List<BoardVO> registered_mylist(String id, BoardVO vo, Criteria cri);
	
	//내가 참여한 리스트 조회
//	List<BoardVO> participate_mylist(String id, BoardVO vo, Criteria cri);


	//총 등록수
	int registered_mylist_count(String id);

	// 게시글 수정
	void myupdate(BoardVO vo);

	// 하윤게시글 삭제
	void mydelete(int seq);

	//글 등록 전 등록 될 일련번호 획득
	int getBoardSeq();
	
	//카테고리 글 개수 
	List<Map<String, String>> category_count();

	int category_count_grid();
	
	// ************* 원준 ************************  
	
	List<Map<String, String>> subscribe_list(BoardVO vo);
	
	// 원준 - 상품 디테일
	Map<String, String> details(int seq);

	int hitcount(int seq);

	BoardVO mydetails(int seq);
	
	//좋아요 기능
	int likeCheck(Map<String, Object> paramMap);

	// ************* 철우 ************************ 
	
	// 철우 - 첨부파일 업로드
	void insertAttachFileList(List<AttachVO> fileList);

	List<Map<String, String>> getsharinglist(BoardVO vo, Criteria cri); 

	// 철우 - 페이징

	int selectRegisterCount(BoardVO vo);

	// 하윤 페이징 grid - 쉐어링 카테고리별로 total 개수 다르게 지정하기
	int selectCategoryRegisterCount(BoardVO vo);
	
	List<Map<String, String>> getindexsharinglist(BoardVO vo);
	
	List<Map<String, String>> getpopularlist(BoardVO vo);
	
	//철우 마이페이지  - 내가 등록학 쉐어링 - 페이징 처리
	List<Map<String, String>> getpagesharinglist(BoardVO vo, Criteria cri);
	
	
	/*************** 진형 관리자 페이지 ******************/
   //문의 게시글 목록
   List<QueVO> getQuestionList();
   
   //관리자 문의삭제 -진형
   void QuestionDelete(int seq);
   
   //문의 답변 페이지
   QueVO QueAnwser(int seq);
   
   //문의 답변 작성
    void QueUpdate(QueVO vo);
    
    //예진
    void deleteBoardLike(int seq);
    
    /*************** 하윤 관리자 글 등록 ******************/
    //하윤 관리자 페이지 - 글 등록
    void admin_register(AdminVO vo);

    //List<BoardVO> admin_register_list();
    
    List<Map<String, String>> admin_getsharinglist(AdminVO vo, Criteria cri); 
   
}
