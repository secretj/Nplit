package com.nplit.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nplit.common.FileUtils;
import com.nplit.service.BoardService;
import com.nplit.service.ChatService;
import com.nplit.vo.AdminVO;
import com.nplit.vo.AttachVO;
import com.nplit.vo.BoardVO;
import com.nplit.vo.ChatMemberListVO;
import com.nplit.vo.ChatRoomVO;
import com.nplit.vo.Criteria;
import com.nplit.vo.MemberVO;
import com.nplit.vo.PageVO;
import com.nplit.vo.QueVO;

@Controller
//board로 model 저장된 객체가 있으면 HttpSession 데이터 보관소에서 동일한 키 값(board)로 저장
//@SessionAttributes("board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
   private ChatService chatService;
	
	//@ModelAttribute : 1. Command 객체 이름 지정
	//					2. View(JSP)에서 사용할 데이터 설정
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		//리턴 값은 ReqeustServlet 데이터 보관소에 저장
		//conditionMap이라는 키 값으로 데이터가 저장
		return conditionMap;
	}
	
	//*******************  main  **************************
	@RequestMapping(value = "/index")
    public String main(BoardVO vo, Model model, AdminVO avo, Criteria cri){
		model.addAttribute("registerlist", boardService.getindexsharinglist(vo));
		model.addAttribute("popularlist", boardService.getpopularlist(vo));
		model.addAttribute("category_count", boardService.category_count());
		model.addAttribute("admin_registerlist", boardService.admin_getsharinglist(avo, cri));
		model.addAttribute("subscribe_list",boardService.subscribe_list(vo));
        return "/main/index";
    }
	
	//***************** notice *******************************
	@RequestMapping(value = "/notice")
    public String notice(){
        return "/main/notice";
    }
	

	/******* 하윤 처음 nearme 페이지에 왔을 때 화면 **********/
	@RequestMapping(value = "/near_me")
    public String near_me(BoardVO vo, Model model, Criteria cri){
		model.addAttribute("registerlist", boardService.getsharinglist(vo, cri));
        return "/near/near_me";
    }
	
	/************** 하윤 nearme 페이지 -> 검색후 목록이 나옴  ******************/
	@RequestMapping("/registerlist_nearme")
	public String registerlist_nearme(BoardVO vo, Model model, Criteria cri) throws Exception {
		if(vo.getSearchAddress() == null) {
			vo.setSearchAddress("");
		}
		System.out.println("vo.getSearchAddress()==============================================" + vo.getSearchAddress());
		
	   cri.setAmount(9);
	   
	   int total = boardService.selectRegisterCount(vo);
	   System.out.println(total);
	   
	   model.addAttribute("registerlist", boardService.getsharinglist(vo, cri));
	   
		return "/near/near_me";
   }
	
	//*******************  채팅 페이지 이동  **************************
	

	@RequestMapping(value = "/chatting")
    public String chatting(){
        return "/chatting/chatting";
    }
	
	//*******************  sharing  **************************
	
	
	// 글 등록 화면으로 이동(url 직접 입력, a태그 클릭)
	@RequestMapping(value="/register_view")
	public String register_view() throws Exception {
		return "/sharing/register";
	}

	// 글 등록 후 -> 상세 보기 페이지로 이동
	@RequestMapping("/register")
	public String register(BoardVO vo,ChatRoomVO crvo,ChatMemberListVO cmlvo, Model model, HttpServletRequest request, MultipartHttpServletRequest mhsr) throws IOException {

	int seq = boardService.getBoardSeq();	
	HttpSession session = request.getSession();
	MemberVO loginInfo = (MemberVO)session.getAttribute("login");
	String id = loginInfo.getMemberId();
	vo.setWriter(id);

	boardService.register(vo);
	
	   //채팅방 개설
	   crvo.setRoomId(seq);
	   chatService.craeteRoom(crvo);
	   
	   //방장 채팅방 참가
	   cmlvo.setRoomId(seq);
	   cmlvo.setMemberId(id);
	   chatService.joinRoomMaster(cmlvo);
	
	//첨부파일 등록
      FileUtils fileUtils = new FileUtils();
      List<AttachVO> fileList = fileUtils.parseFileInfo(seq, request, mhsr);
      
      if(CollectionUtils.isEmpty(fileList) == false) {
         boardService.insertAttachFileList(fileList);
      }
	return "redirect:/details?seq=" + seq;
	}
	

	
	//쉐어링 목록 화면 - 그리드(기본) 
	@RequestMapping("/registerlist")
	public String registerlist(BoardVO vo, Model model, Criteria cri) throws Exception {
		System.out.println("cate==========================================" + vo.getCategory());
		
		if(vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}
		
		if(vo.getSearchCategory() == null) {
			vo.setSearchCategory("");
		}
		
		if(vo.getSearchAddress() == null) {
			vo.setSearchAddress("");
		}
		System.out.println("vo.getSearchAddress()==============================================" + vo.getSearchAddress());
		//	     if(vo.getSearchCondition() == null) {
		//      vo.setSearchCondition("TITLE");
		//      }
		//   if(vo.getSearchKeyword() == null) {
		//        vo.setSearchKeyword("");
		//       }
	    
	   System.out.println("글 상세 조회 처리");
	   System.out.println(cri.getPageNum());
	   System.out.println(cri.getAmount());
	   //mypage list에서 조회쿼리 호출 전에 cri.setAmount(4);
	   cri.setAmount(9);
	   int total = 0;
	   //System.out.println(vo.getCategory());
	   // 하윤 페이징 grid - 쉐어링 카테고리별로 total 개수 다르게 지정하기
	   if(vo.getCategory() == null || vo.getCategory().equals("")) {
		   total = boardService.selectRegisterCount(vo);
		   System.out.println("11111111111111111" + vo.getCategory());
	   }
	   else {
		   total = boardService.selectCategoryRegisterCount(vo);
		   System.out.println("2222222222222222" + vo.getCategory());
	   }
	   System.out.println(total);
	   
	   model.addAttribute("registerlist", boardService.getsharinglist(vo, cri));
	   model.addAttribute("category_count", boardService.category_count()); // 카테고리별 개수
	   model.addAttribute("pageMaker", new PageVO(cri, total));
	   
	   System.out.println(boardService.getsharinglist(vo, cri));
	   
		//하윤 카테고리 
		model.addAttribute("current_category", vo.getCategory());
	
		return "/sharing/grid";
   }

	
	//*******************  mypage - sharing  **************************
	
	// 하윤 마이페이지 쉐어링 목록 보기
	@RequestMapping(value = "/sharing_registered")
    public String registered_mylist(BoardVO vo, Criteria cri, Model model, HttpServletRequest request) throws Exception {
		

		HttpSession session = request.getSession();
		MemberVO loginInfo = (MemberVO)session.getAttribute("login");
		String id = loginInfo.getMemberId();
		
		// 페이징처리 수정 
		cri.setAmount(4);
		int total = boardService.registered_mylist_count(id); //total = 내가 등록한 쉐어링 총 개수
		System.out.println("**** 내가 등록한 쉐어링 총 개수" + total);
		
		model.addAttribute("mylist", boardService.registered_mylist(id, vo, cri));
		model.addAttribute("pageMaker", new PageVO(cri, total));
		model.addAttribute("mylist_count", total);

        return "/mypage/sharing_registered";
    }
	

	
	   
	  // 하윤 마이페이지 - 쉐어링 삭제
    @RequestMapping(value = "/mypage/sharing_registered/delete")
     public String mypage_sharing_delete(@RequestParam("seq") int seq) {
       boardService.mydelete(seq);
       boardService.deleteFile(seq);
       
       
       //예진 글 삭제 -> 좋아요 테이블 삭제
       boardService.deleteBoardLike(seq);
       // 글삭제 -> 채팅 관련 테이블 정보 삭제 
       boardService.deleteBoardChatMemberList(seq);
       boardService.deleteBoardChatMessage(seq);
       boardService.deleteBoardChatRoom(seq);
       return "redirect:/sharing_registered";
     }

	
	// 마이페이지에서 글 수정 화면으로 이동(url 직접 입력, a태그 클릭)
	@RequestMapping(value="/myupdate_view")
	public String update_view(@RequestParam("seq") int seq, Model model) {
		BoardVO vo = boardService.mydetails(seq);
		System.out.println(vo.toString());
		model.addAttribute("myupdate",boardService.mydetails(seq));
		
		model.addAttribute("fileList", boardService.getBoardFileList(seq));
		return "/sharing/update";
	}
	
	   //하윤 글 수정 기능
	   //ModelAttribute로 세션에 board라는 이름으로 저장된 객체가 있는지 찾아서 Command객체에 담아줌
	      @RequestMapping(value="/myupdate")
	      public String myupdate(@ModelAttribute("myupdate") BoardVO vo, Model model, HttpSession session) throws IOException {
	         System.out.println("글 수정 처리");
	         System.out.println("일련번호 : " + vo.getSeq());
	         System.out.println("제목 : " + vo.getTitle());
	         System.out.println("작성자 이름 : " + vo.getWriter());
	         System.out.println("내용 : " + vo.getContents());
	         System.out.println("등록일 : " + vo.getRegDate());
	         System.out.println("조회수 : " + vo.getHit());
	         
	         int seq = vo.getSeq();

	         boardService.myupdate(vo);
	         model.addAttribute("board", boardService.mydetails(seq));
	         model.addAttribute("fileList", boardService.getBoardFileList(seq));
	         model.addAttribute("member", (MemberVO)session.getAttribute("login"));
	         return "redirect:/details?seq=" + vo.getSeq();
	      }
	
	      // 하윤 delete 기능 구현
	         @RequestMapping(value="/mydelete")
	         public String mydelete(int seq) {
	            System.out.println("글 삭제 처리");
	            boardService.deleteFile(seq);
	            boardService.mydelete(seq);
	            //예진 글 삭제 -> 좋아요 테이블 삭제
	            boardService.deleteBoardLike(seq);
	            // 글삭제 -> 채팅 관련 테이블 정보 삭제 
	            boardService.deleteBoardChatMemberList(seq);
	            boardService.deleteBoardChatMessage(seq);
	            boardService.deleteBoardChatRoom(seq);
	            return "redirect:/sharing_registered";
	         }
    
	   // 하윤 마이페이지 - 쉐어링 상세보기
	      @RequestMapping(value = "/sharing_registered/details")
	       public String mypage_sharing_details(@RequestParam("seq") int seq, Model model,HttpSession session) {
	         MemberVO loginInfo = (MemberVO)session.getAttribute("login");
	         String memberId= loginInfo.getMemberId();
	         
	         //boardService.hitcount(seq); //조회수를 먼저 업데이트하고
	         
	         Map<String, Object> paramMap = new HashMap<String, Object>();
	          
	          paramMap.put("seq", seq);
	          paramMap.put("memberId", memberId);
	         
	       // 상품을 조회해야함 (위아래 순서가 바뀌어있어서 조회수가 0부터 뜬 것임)
	          Map<String, String> resultMap = boardService.details(seq);
	          System.out.println("===================>" + resultMap);
	          
	         model.addAttribute("board", boardService.mydetails(seq));
	         model.addAttribute("likeCheck", boardService.likeCheck(paramMap));
	         model.addAttribute("fileList", boardService.getBoardFileList(seq));
	         model.addAttribute("member",loginInfo);
	         
	      
	         
	         BoardVO vo = boardService.mydetails(seq);
	         System.out.println(vo.getHit());
	         
	           return "/sharing/details";
	       }
	      
	      
	      
    //원준 - 상세페이지
      @RequestMapping("/details")
      public String details(@RequestParam("seq") int seq, Model model, BoardVO vo, HttpSession session) {
                
    	  if((MemberVO)session.getAttribute("login")==null) {
    		  boardService.hitcount(seq); //조회수를 먼저 업데이트하고
   	          
   	          Map<String, Object> paramMap = new HashMap<String, Object>();
   	          
   	          paramMap.put("seq", seq);
   	       // 상품을 조회해야함 (위아래 순서가 바뀌어있어서 조회수가 0부터 뜬 것임)
   	          Map<String, String> resultMap = boardService.details(seq);
   	          System.out.println("===================>" + resultMap);
   	          
   	          model.addAttribute("board",boardService.details(seq));
   	         
   	          model.addAttribute("fileList", boardService.getBoardFileList(seq));
   	          
    	  }else {
    		  MemberVO loginInfo = (MemberVO)session.getAttribute("login");
   	       String memberId= loginInfo.getMemberId();
   	    	  
   	          boardService.hitcount(seq); //조회수를 먼ㅁ저 업데이트하고
   	          
   	          Map<String, Object> paramMap = new HashMap<String, Object>();
   	          
   	          paramMap.put("seq", seq);
   	          paramMap.put("memberId", memberId);
   	      
   	          
   	           // 상품을 조회해야함 (위아래 순서가 바뀌어있어서 조회수가 0부터 뜬 것임)
   	          Map<String, String> resultMap = boardService.details(seq);
   	          System.out.println("===================>" + resultMap);
   	          
   	          model.addAttribute("board",boardService.details(seq));
   	          model.addAttribute("likeCheck", boardService.likeCheck(paramMap));
   	          model.addAttribute("fileList", boardService.getBoardFileList(seq));
    	  }
	  
          return "/sharing/details";
       }
      
      
      
	
//	@GetMapping(value="/sharing_list")
//	public String list() {
//		return "insertBoard";
//	}
//
//	@GetMapping(value="/insertBoard.do")
//	public String insertBoardView() {
//		return "insertBoard";
//	}
	
	@PostMapping("/insertBoard.do")
	//Command 객체 : 사용자가 전송한 데이터를 매핑한 VO를 바로 생성
	//				사용자 입력 값이 많아지면 코드가 길어지기 때문에 간략화 가능
	//              사용자 입력 input의 name 속성과 VO 멤버변수의 이름을 매핑해주는 것이 중요
	public String insertBoard(BoardVO vo, HttpServletRequest request,
		MultipartHttpServletRequest mhsr) throws IOException {
		System.out.println("글 등록 처리");
		boardService.insertBoard(vo);
		
		//화면 네비게이션(게시글 등록 완료 후 게시글 목록으로 이동)
		return "redirect:getBoardList.do";
	}
	
	//ModelAttribute로 세션에 board라는 이름으로 저장된 객체가 있는지 찾아서 Command객체에 담아줌
//	@RequestMapping(value="/updateBoard.do")
//	public String updateBoard(@ModelAttribute("board") BoardVO vo, HttpServletRequest request,
//		MultipartHttpServletRequest mhsr) throws IOException {
//		System.out.println("글 수정 처리");
//		System.out.println("일련번호 : " + vo.getSeq());
//		System.out.println("제목 : " + vo.getTitle());
//		System.out.println("작성자 이름 : " + vo.getWriter());
//		System.out.println("내용 : " + vo.getContents());
//		System.out.println("등록일 : " + vo.getRegDate());
//		System.out.println("조회수 : " + vo.getHit());
//		
//		int seq = vo.getSeq();
//		
//		boardService.updateBoard(vo);
//		return "redirect:getBoardList.do";
//	}
	
//	@RequestMapping(value="/deleteBoard.do")
//	public String deleteBoard(BoardVO vo) {
//		System.out.println("글 삭제 처리");
//		
//		boardService.deleteBoard(vo);
//		boardService.deleteFileList(vo.getSeq());
//		return "redirect:getBoardList.do";
//	}
//	
//	@RequestMapping(value="/getBoard.do")
//	public String getBoard(BoardVO vo, Model model) {
//		System.out.println("글 상세 조회 처리");
//		
//		//Model 객체는 RequestServlet 데이터 보관소에 저장
//		//RequestServlet 데이터 보관소에 저장하는 것과 동일하게 동작
//		//request.setAttribute("board", boardDAO.getBoard(vo)) == model.addAttribute("board", boardDAO.getBoard(vo))
//		model.addAttribute("board", boardService.getBoard(vo));
//		model.addAttribute("fileList", boardService.getBoardFileList(vo.getSeq()));		
//		return "getBoard";
//	}
	
	@RequestMapping(value="/getBoardList.do")
	//@RequestParam : Command 객체인 VO에 매핑값이 없는 사용자 입력정보는 직접 받아서 처리
	//				  value = 화면으로부터 전달된 파라미터 이름(jsp의 input의 name속성 값)
	//				  required = 생략 가능 여부
	public String getBoardList( /*
								 * @RequestParam(value="searchCondition", defaultValue="TITLE", required=false)
								 * String condition,
								 * 
								 * @RequestParam(value="searchKeyword", defaultValue="", required=false) String
								 * keyword,
								 */
								BoardVO vo, Model model) {
		System.out.println("글 목록 검색 처리");
		
		model.addAttribute("boardList", boardService.getBoardList(vo));
		return "getBoardList";
	}
	
	   
	   @RequestMapping(value="/deleteFile")
	   @ResponseBody
	   public void deleteFile(int seq) {
	      boardService.deleteFile(seq);
	   }
	
	@RequestMapping(value="/fileDown.do")
	@ResponseBody
	public ResponseEntity<Resource> fileDown(@RequestParam("fileName") String fileName, 
				HttpServletRequest request) throws Exception {
		//업로드 파일 경로
		String path = request.getSession().getServletContext().getRealPath("/") + "/upload/";
		
		System.out.println(path);
		
		//파일경로, 파일명으로 리소스 객체 생성
		Resource resource = new FileSystemResource(path + fileName);
		
		//파일 명
		String resourceName = resource.getFilename();
		
		//Http헤더에 옵션을 추가하기 위해서 헤더 변수 선언
		HttpHeaders headers = new HttpHeaders();
		
		try {
			//헤더에 파일명으로 첨부파일 추가
			headers.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"),
						"ISO-8859-1"));
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	/*************** 진형 관리자 페이지 ******************/
	
    //1:1문의 관리자 페이지 리스트
   @GetMapping("/admin/inquirepage")
    public void admin_inquirepage(Model model) throws Exception{
       
       model.addAttribute("getQuestionList", boardService.getQuestionList());
   }
   
   // 진형 어드민 - 1:1 문의 삭제
   @RequestMapping(value = "quedelete")
   public String QuestionDelete(@RequestParam("seq") int seq) {
      boardService.QuestionDelete(seq);
      return "redirect:/admin/inquirepage";
    
    }
   
   //진형 - 문의 답변 페이지 조회
   @RequestMapping("/QueAnwser")
   public String QueAnwser(@RequestParam("seq") int seq, Model model) throws Exception {              
       QueVO vo = boardService.QueAnwser(seq);
       model.addAttribute("question", vo);
       
       return "/admin/inquiredetail";
    }
   
   //진형 문의 답변 작성
   //ModelAttribute로 세션에 board라는 이름으로 저장된 객체가 있는지 찾아서 Command객체에 담아줌
      @RequestMapping(value="/QueUpdate")
      public String QueUpdate(@ModelAttribute("question") QueVO vo, Model model) throws IOException {
         int seq = vo.getSeq();
         System.out.println(vo.getSeq());
         boardService.QueUpdate(vo);
         model.addAttribute("question",boardService.QueAnwser(seq));
         
           return "redirect:/admin/inquirepage";
      }
      
      /*************** 하윤 관리자 글 등록 ******************/
      
      //관리자 메인페이지로 이동
      @RequestMapping(value = "/admin_page")
      public String admin_page(AdminVO vo, Model model, Criteria cri){
    	  model.addAttribute("admin_registerlist", boardService.admin_getsharinglist(vo, cri));
          return "/admin/admin_page";
      }
      
      //관리자 메인페이지에서 글등록화면으로 이동
      @RequestMapping(value = "/admin_eventmake_view")
      public String admin_eventmake_view() throws Exception {
          return "/admin/eventmake";
      }
  	
      
  	//관리자 글 등록 후 목록으로 이동
 	@RequestMapping(value = "/admin_register")
 	public String admin_register(AdminVO vo, Model model, HttpServletRequest request, MultipartHttpServletRequest mhsr) throws IOException {

 	int seq = boardService.getBoardSeq();	
 	HttpSession session = request.getSession();
 	MemberVO loginInfo = (MemberVO)session.getAttribute("login");
 	String id = loginInfo.getMemberId();
 	vo.setWriter(id);

 	boardService.admin_register(vo);
 	
 	//첨부파일 등록
   FileUtils fileUtils = new FileUtils();
   List<AttachVO> fileList = fileUtils.parseFileInfo(seq, request, mhsr);
   
   if(CollectionUtils.isEmpty(fileList) == false) {
      boardService.insertAttachFileList(fileList);
   }
       return "redirect:/admin_registerlist";
 	}
 	
 	//관리자 글 등록 화면
	@RequestMapping("/admin_registerlist")
	public String admin_registerlist(AdminVO vo, Model model, Criteria cri) throws Exception {
		System.out.println("cate=============" + vo.getCategory());
		
	   model.addAttribute("admin_registerlist", boardService.admin_getsharinglist(vo, cri));
	
	   //System.out.println(boardService.getsharinglist(vo, cri));
	   
		//하윤 카테고리 
		//model.addAttribute("current_category", vo.getCategory());
	
		return "/admin/admin_page";
   }
      
}
