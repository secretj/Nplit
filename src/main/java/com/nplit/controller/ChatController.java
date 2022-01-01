package com.nplit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nplit.service.ChatService;
import com.nplit.vo.BoardVO;
import com.nplit.vo.ChatMemberListVO;
import com.nplit.vo.ChatMessageVO;
import com.nplit.vo.ChatRoomVO;
import com.nplit.vo.Criteria;
import com.nplit.vo.MemberVO;
import com.nplit.vo.PageVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {
	@Inject
	ChatService service;

	// ä�ù� ����
	@RequestMapping(value = "/roomJoin")
	public String roomJoin(@RequestParam("roomId") int roomId, ChatMemberListVO cmlvo, Model model,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		MemberVO loginInfo = (MemberVO) session.getAttribute("login");
		String memberId = loginInfo.getMemberId();
		cmlvo.setRoomId(roomId);
		cmlvo.setMemberId(memberId);
		if (service.chatcheck(cmlvo) == 0) {
			service.roomJoin(cmlvo);
		}

		// ä�ù� ���� �ҷ�����
		ChatRoomVO vo = service.selectChattingDetail(roomId);
		model.addAttribute("selectMyChatting", vo);
		model.addAttribute("chatcheck", service.chatcheck(cmlvo));
		return "redirect:/selectMessage?roomId=" + roomId;

	}

	// ���� ������ ä�� ����Ʈ�̾ƿ���
	@RequestMapping("/selectMyChatting")
	public String selectMyChatting(HttpSession session, ChatMemberListVO cmlvo, Model model) {

		MemberVO loginInfo = (MemberVO) session.getAttribute("login"); // �α��� ���� �̾ƿ���
		// loginInfo��� ������ �α��� ���̵� ����

		model.addAttribute("selectMyChatting", service.selectMyChatting(loginInfo.getMemberId()));
		model.addAttribute("chatcheck", service.chatcheck(cmlvo));

		return "chatting/chatting";
	}

	// ���� - ä�ù� ���� ��ȸ
	@RequestMapping("/selectChattingDetail")
	public String selectChattingDetail(@RequestParam("roomId") int seq, Model model) {
		ChatRoomVO vo = service.selectChattingDetail(seq);
		model.addAttribute("selectChattingDetail", vo);
		return "chatting/chattingdetail";
	}

	// �޼��� ����
	@RequestMapping(value = "/insertSendMsg")
	public void insertSendMsg(@RequestParam("roomId") int roomId, ChatMessageVO vo, HttpServletRequest request) {

		HttpSession session = request.getSession();
		MemberVO loginInfo = (MemberVO) session.getAttribute("login");
		String memberId = loginInfo.getMemberId();

		vo.setRoomId(roomId);
		vo.setSendId(memberId);
		vo.setFullFilePath(loginInfo.getFullFilePath());
		service.insertSendMsg(vo);

	}

	// ���� - �ش� ���� �޼��� ��ȸ
	@RequestMapping("/selectMessage")
	public String selectMessage(@RequestParam("roomId") int seq, Model model) {
		List<ChatMessageVO> cm = service.selectMessage(seq);
		model.addAttribute("selectMessage", cm);
		ChatRoomVO cr = service.selectChattingDetail(seq);
		model.addAttribute("selectChattingDetail", cr);
		return "chatting/chattingdetail";
	}

	@RequestMapping("/sharing_participate")
	public String sharing_participate(Model model, HttpSession session, ChatMemberListVO vo, BoardVO bvo,
			Criteria cri) {
		MemberVO parInfo = (MemberVO) session.getAttribute("login");
		String parId = parInfo.getMemberId();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parId", parId);
		paramMap.put("board", bvo);
		cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());
		paramMap.put("criteria", cri);
		System.out.println(bvo);
		cri.setAmount(4);
		int total = service.getListCount(parId);
		model.addAttribute("pageMaker", new PageVO(cri, total));
		model.addAttribute("selectMemberList", service.selectMemberList(paramMap));
		model.addAttribute("getListCount", service.getListCount(parId));
		model.addAttribute("mylist_count", total);

		return "/mypage/sharing_participate";
	}

}