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
//board�� model ����� ��ü�� ������ HttpSession ������ �����ҿ��� ������ Ű ��(board)�� ����
//@SessionAttributes("board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private ChatService chatService;

	// @ModelAttribute : 1. Command ��ü �̸� ����
	// 2. View(JSP)���� ����� ������ ����
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("����", "TITLE");
		conditionMap.put("����", "CONTENT");
		// ���� ���� ReqeustServlet ������ �����ҿ� ����
		// conditionMap�̶�� Ű ������ �����Ͱ� ����
		return conditionMap;
	}

	// ******************* main **************************
	@RequestMapping(value = "/index")
	public String main(BoardVO vo, Model model, AdminVO avo, Criteria cri) {
		model.addAttribute("registerlist", boardService.getindexsharinglist(vo));
		model.addAttribute("popularlist", boardService.getpopularlist(vo));
		model.addAttribute("category_count", boardService.category_count());
		model.addAttribute("admin_registerlist", boardService.admin_getsharinglist(avo, cri));
		model.addAttribute("subscribe_list", boardService.subscribe_list(vo));
		return "/main/index";
	}

	// ***************** notice *******************************
	@RequestMapping(value = "/notice")
	public String notice() {
		return "/main/notice";
	}

	/******* ���� ó�� nearme �������� ���� �� ȭ�� **********/
	@RequestMapping(value = "/near_me")
	public String near_me(BoardVO vo, Model model, Criteria cri) {
		model.addAttribute("registerlist", boardService.getsharinglist(vo, cri));
		return "/near/near_me";
	}

	/************** ���� nearme ������ -> �˻��� ����� ���� ******************/
	@RequestMapping("/registerlist_nearme")
	public String registerlist_nearme(BoardVO vo, Model model, Criteria cri) throws Exception {
		if (vo.getSearchAddress() == null) {
			vo.setSearchAddress("");
		}
		System.out
				.println("vo.getSearchAddress()==============================================" + vo.getSearchAddress());

		cri.setAmount(9);

		int total = boardService.selectRegisterCount(vo);
		System.out.println(total);

		model.addAttribute("registerlist", boardService.getsharinglist(vo, cri));

		return "/near/near_me";
	}

	// ******************* ä�� ������ �̵� **************************

	@RequestMapping(value = "/chatting")
	public String chatting() {
		return "/chatting/chatting";
	}

	// ******************* sharing **************************

	// �� ��� ȭ������ �̵�(url ���� �Է�, a�±� Ŭ��)
	@RequestMapping(value = "/register_view")
	public String register_view() throws Exception {
		return "/sharing/register";
	}

	// �� ��� �� -> �� ���� �������� �̵�
	@RequestMapping("/register")
	public String register(BoardVO vo, ChatRoomVO crvo, ChatMemberListVO cmlvo, Model model, HttpServletRequest request,
			MultipartHttpServletRequest mhsr) throws IOException {

		int seq = boardService.getBoardSeq();
		HttpSession session = request.getSession();
		MemberVO loginInfo = (MemberVO) session.getAttribute("login");
		String id = loginInfo.getMemberId();
		vo.setWriter(id);

		boardService.register(vo);

		// ä�ù� ����
		crvo.setRoomId(seq);
		chatService.craeteRoom(crvo);

		// ���� ä�ù� ����
		cmlvo.setRoomId(seq);
		cmlvo.setMemberId(id);
		chatService.joinRoomMaster(cmlvo);

		// ÷������ ���
		FileUtils fileUtils = new FileUtils();
		List<AttachVO> fileList = fileUtils.parseFileInfo(seq, request, mhsr);

		if (CollectionUtils.isEmpty(fileList) == false) {
			boardService.insertAttachFileList(fileList);
		}
		return "redirect:/details?seq=" + seq;
	}

	// ��� ��� ȭ�� - �׸���(�⺻)
	@RequestMapping("/registerlist")
	public String registerlist(BoardVO vo, Model model, Criteria cri) throws Exception {
		System.out.println("cate==========================================" + vo.getCategory());

		if (vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}

		if (vo.getSearchCategory() == null) {
			vo.setSearchCategory("");
		}

		if (vo.getSearchAddress() == null) {
			vo.setSearchAddress("");
		}

		cri.setAmount(9);
		int total = 0;

		if (vo.getCategory() == null || vo.getCategory().equals("")) {
			total = boardService.selectRegisterCount(vo);
			System.out.println("11111111111111111" + vo.getCategory());
		} else {
			total = boardService.selectCategoryRegisterCount(vo);
			System.out.println("2222222222222222" + vo.getCategory());
		}
		System.out.println(total);

		model.addAttribute("registerlist", boardService.getsharinglist(vo, cri));
		model.addAttribute("category_count", boardService.category_count()); // ī�װ��� ����
		model.addAttribute("pageMaker", new PageVO(cri, total));

		System.out.println(boardService.getsharinglist(vo, cri));

		// ���� ī�װ�
		model.addAttribute("current_category", vo.getCategory());

		return "/sharing/grid";
	}

	// ******************* mypage - sharing **************************

	// ���� ���������� ��� ��� ����
	@RequestMapping(value = "/sharing_registered")
	public String registered_mylist(BoardVO vo, Criteria cri, Model model, HttpServletRequest request)
			throws Exception {

		HttpSession session = request.getSession();
		MemberVO loginInfo = (MemberVO) session.getAttribute("login");
		String id = loginInfo.getMemberId();

		// ����¡ó�� ����
		cri.setAmount(4);
		int total = boardService.registered_mylist_count(id); // total = ���� ����� ��� �� ����
		System.out.println("**** ���� ����� ��� �� ����" + total);

		model.addAttribute("mylist", boardService.registered_mylist(id, vo, cri));
		model.addAttribute("pageMaker", new PageVO(cri, total));
		model.addAttribute("mylist_count", total);

		return "/mypage/sharing_registered";
	}

	// ���� ���������� - ��� ����
	@RequestMapping(value = "/mypage/sharing_registered/delete")
	public String mypage_sharing_delete(@RequestParam("seq") int seq) {
		boardService.mydelete(seq);
		boardService.deleteFile(seq);

		// ���� �� ���� -> ���ƿ� ���̺� ����
		boardService.deleteBoardLike(seq);
		// �ۻ��� -> ä�� ���� ���̺� ���� ����
		boardService.deleteBoardChatMemberList(seq);
		boardService.deleteBoardChatMessage(seq);
		boardService.deleteBoardChatRoom(seq);
		return "redirect:/sharing_registered";
	}

	// �������������� �� ���� ȭ������ �̵�(url ���� �Է�, a�±� Ŭ��)
	@RequestMapping(value = "/myupdate_view")
	public String update_view(@RequestParam("seq") int seq, Model model) {
		BoardVO vo = boardService.mydetails(seq);
		System.out.println(vo.toString());
		model.addAttribute("myupdate", boardService.mydetails(seq));

		model.addAttribute("fileList", boardService.getBoardFileList(seq));
		return "/sharing/update";
	}

	// ���� �� ���� ���
	// ModelAttribute�� ���ǿ� board��� �̸����� ����� ��ü�� �ִ��� ã�Ƽ� Command��ü��
	// �����
	@RequestMapping(value = "/myupdate")
	public String myupdate(@ModelAttribute("myupdate") BoardVO vo, Model model, HttpSession session)
			throws IOException {

		int seq = vo.getSeq();

		boardService.myupdate(vo);
		model.addAttribute("board", boardService.mydetails(seq));
		model.addAttribute("fileList", boardService.getBoardFileList(seq));
		model.addAttribute("member", (MemberVO) session.getAttribute("login"));
		return "redirect:/details?seq=" + vo.getSeq();
	}

	// ���� delete ��� ����
	@RequestMapping(value = "/mydelete")
	public String mydelete(int seq) {
		System.out.println("�� ���� ó��");
		boardService.deleteFile(seq);
		boardService.mydelete(seq);
		// ���� �� ���� -> ���ƿ� ���̺� ����
		boardService.deleteBoardLike(seq);
		// �ۻ��� -> ä�� ���� ���̺� ���� ����
		boardService.deleteBoardChatMemberList(seq);
		boardService.deleteBoardChatMessage(seq);
		boardService.deleteBoardChatRoom(seq);
		return "redirect:/sharing_registered";
	}

	// ���� ���������� - ��� �󼼺���
	@RequestMapping(value = "/sharing_registered/details")
	public String mypage_sharing_details(@RequestParam("seq") int seq, Model model, HttpSession session) {
		MemberVO loginInfo = (MemberVO) session.getAttribute("login");
		String memberId = loginInfo.getMemberId();

		// boardService.hitcount(seq); //��ȸ���� ���� ������Ʈ�ϰ�

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("seq", seq);
		paramMap.put("memberId", memberId);

		// ��ǰ�� ��ȸ�ؾ��� (���Ʒ� ������ �ٲ���־ ��ȸ���� 0���� �� ����)
		Map<String, String> resultMap = boardService.details(seq);

		model.addAttribute("board", boardService.mydetails(seq));
		model.addAttribute("likeCheck", boardService.likeCheck(paramMap));
		model.addAttribute("fileList", boardService.getBoardFileList(seq));
		model.addAttribute("member", loginInfo);

		BoardVO vo = boardService.mydetails(seq);

		return "/sharing/details";
	}

	// ���� - ��������
	@RequestMapping("/details")
	public String details(@RequestParam("seq") int seq, Model model, BoardVO vo, HttpSession session) {

		if ((MemberVO) session.getAttribute("login") == null) {
			boardService.hitcount(seq); // ��ȸ���� ���� ������Ʈ�ϰ�

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("seq", seq);
			// ��ǰ�� ��ȸ�ؾ��� (���Ʒ� ������ �ٲ���־ ��ȸ���� 0���� �� ����)
			Map<String, String> resultMap = boardService.details(seq);
			System.out.println("===================>" + resultMap);

			model.addAttribute("board", boardService.details(seq));

			model.addAttribute("fileList", boardService.getBoardFileList(seq));

		} else {
			MemberVO loginInfo = (MemberVO) session.getAttribute("login");
			String memberId = loginInfo.getMemberId();

			boardService.hitcount(seq); // ��ȸ���� �դ��� ������Ʈ�ϰ�

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("seq", seq);
			paramMap.put("memberId", memberId);

			// ��ǰ�� ��ȸ�ؾ��� (���Ʒ� ������ �ٲ���־ ��ȸ���� 0���� �� ����)
			Map<String, String> resultMap = boardService.details(seq);
			System.out.println("===================>" + resultMap);

			model.addAttribute("board", boardService.details(seq));
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
	// Command ��ü : ����ڰ� ������ �����͸� ������ VO�� �ٷ� ����
	// ����� �Է� ���� �������� �ڵ尡 ������� ������ ����ȭ ����
	// ����� �Է� input�� name �Ӽ��� VO ��������� �̸��� �������ִ� ���� �߿�
	public String insertBoard(BoardVO vo, HttpServletRequest request, MultipartHttpServletRequest mhsr)
			throws IOException {
		System.out.println("�� ��� ó��");
		boardService.insertBoard(vo);

		// ȭ�� �׺���̼�(�Խñ� ��� �Ϸ� �� �Խñ� ������� �̵�)
		return "redirect:getBoardList.do";
	}

	@RequestMapping(value = "/getBoardList.do")
	// @RequestParam : Command ��ü�� VO�� ���ΰ��� ���� ����� �Է������� ���� �޾Ƽ� ó��
	// value = ȭ�����κ��� ���޵� �Ķ���� �̸�(jsp�� input�� name�Ӽ� ��)
	// required = ���� ���� ����
	public String getBoardList( /*
								 * @RequestParam(value="searchCondition", defaultValue="TITLE", required=false)
								 * String condition,
								 * 
								 * @RequestParam(value="searchKeyword", defaultValue="", required=false) String
								 * keyword,
								 */
			BoardVO vo, Model model) {
		System.out.println("�� ��� �˻� ó��");

		model.addAttribute("boardList", boardService.getBoardList(vo));
		return "getBoardList";
	}

	@RequestMapping(value = "/deleteFile")
	@ResponseBody
	public void deleteFile(int seq) {
		boardService.deleteFile(seq);
	}

	@RequestMapping(value = "/fileDown.do")
	@ResponseBody
	public ResponseEntity<Resource> fileDown(@RequestParam("fileName") String fileName, HttpServletRequest request)
			throws Exception {
		// ���ε� ���� ���
		String path = request.getSession().getServletContext().getRealPath("/") + "/upload/";

		System.out.println(path);

		// ���ϰ��, ���ϸ����� ���ҽ� ��ü ����
		Resource resource = new FileSystemResource(path + fileName);

		// ���� ��
		String resourceName = resource.getFilename();

		// Http����� �ɼ��� �߰��ϱ� ���ؼ� ��� ���� ����
		HttpHeaders headers = new HttpHeaders();

		try {
			// ����� ���ϸ����� ÷������ �߰�
			headers.add("Content-Disposition",
					"attachment; filename=" + new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}

	/*************** ���� ������ ������ ******************/

	// 1:1���� ������ ������ ����Ʈ
	@GetMapping("/admin/inquirepage")
	public void admin_inquirepage(Model model) throws Exception {

		model.addAttribute("getQuestionList", boardService.getQuestionList());
	}

	// ���� ���� - 1:1 ���� ����
	@RequestMapping(value = "quedelete")
	public String QuestionDelete(@RequestParam("seq") int seq) {
		boardService.QuestionDelete(seq);
		return "redirect:/admin/inquirepage";

	}

	// ���� - ���� �亯 ������ ��ȸ
	@RequestMapping("/QueAnwser")
	public String QueAnwser(@RequestParam("seq") int seq, Model model) throws Exception {
		QueVO vo = boardService.QueAnwser(seq);
		model.addAttribute("question", vo);

		return "/admin/inquiredetail";
	}

	// ���� ���� �亯 �ۼ�
	// ModelAttribute�� ���ǿ� board��� �̸����� ����� ��ü�� �ִ��� ã�Ƽ� Command��ü��
	// �����
	@RequestMapping(value = "/QueUpdate")
	public String QueUpdate(@ModelAttribute("question") QueVO vo, Model model) throws IOException {
		int seq = vo.getSeq();
		System.out.println(vo.getSeq());
		boardService.QueUpdate(vo);
		model.addAttribute("question", boardService.QueAnwser(seq));

		return "redirect:/admin/inquirepage";
	}

	/*************** ���� ������ �� ��� ******************/

	// ������ ������������ �̵�
	@RequestMapping(value = "/admin_page")
	public String admin_page(AdminVO vo, Model model, Criteria cri) {
		model.addAttribute("admin_registerlist", boardService.admin_getsharinglist(vo, cri));
		return "/admin/admin_page";
	}

	// ������ �������������� �۵��ȭ������ �̵�
	@RequestMapping(value = "/admin_eventmake_view")
	public String admin_eventmake_view() throws Exception {
		return "/admin/eventmake";
	}

	// ������ �� ��� �� ������� �̵�
	@RequestMapping(value = "/admin_register")
	public String admin_register(AdminVO vo, Model model, HttpServletRequest request, MultipartHttpServletRequest mhsr)
			throws IOException {

		int seq = boardService.getBoardSeq();
		HttpSession session = request.getSession();
		MemberVO loginInfo = (MemberVO) session.getAttribute("login");
		String id = loginInfo.getMemberId();
		vo.setWriter(id);

		boardService.admin_register(vo);

		// ÷������ ���
		FileUtils fileUtils = new FileUtils();
		List<AttachVO> fileList = fileUtils.parseFileInfo(seq, request, mhsr);

		if (CollectionUtils.isEmpty(fileList) == false) {
			boardService.insertAttachFileList(fileList);
		}
		return "redirect:/admin_registerlist";
	}

	// ������ �� ��� ȭ��
	@RequestMapping("/admin_registerlist")
	public String admin_registerlist(AdminVO vo, Model model, Criteria cri) throws Exception {

		model.addAttribute("admin_registerlist", boardService.admin_getsharinglist(vo, cri));

		return "/admin/admin_page";
	}

}
