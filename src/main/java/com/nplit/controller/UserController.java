package com.nplit.controller;

import java.io.File;
import java.sql.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.nplit.service.UserService;
import com.nplit.vo.MemberVO;
import com.nplit.vo.QueVO;

@Controller
public class UserController {

	@Inject // byType으로 자동 주입
	UserService service;

	// 예진 logger 선언
	private static final Logger l = LoggerFactory.getLogger(UserController.class);

	/******************* 구글 로그인 ****************************/

	// 예진 구글 로그인 post
	@ResponseBody
	@RequestMapping(value = "/loginGoogle")
	public String loginGooglePOST(MemberVO vo, HttpSession session, RedirectAttributes rttr, MemberVO mvo)
			throws Exception {
		MemberVO returnVO = service.loginMemberByGoogle(vo);
		String mvo_ajaxid = mvo.getMemberId();

		if (returnVO == null) { // 아이디가 DB에 존재하지 않는 경우
			// 구글 회원가입
			service.joinMemberByGoogle(vo);
			// 구글 로그인
			returnVO = service.loginMemberByGoogle(vo);
			session.setAttribute("id", returnVO.getMemberId());
			// 구글 로그인 이후 마이페이지 접근 안되는 문제 해결 위해 추가
			session.setAttribute("login", returnVO);
			rttr.addFlashAttribute("mvo", returnVO);
		}

		if (mvo_ajaxid.equals(returnVO.getMemberId())) { // 아이디가 DB에 존재하는 경우
			// 구글 로그인
			service.loginMemberByGoogle(vo);
			session.setAttribute("id", returnVO.getMemberId());
			// 구글 로그인 이후 마이페이지 접근 안되는 문제 해결 위해 추가
			session.setAttribute("login", returnVO);
			rttr.addFlashAttribute("mvo", returnVO);
		} else {// 아이디가 DB에 존재하지 않는 경우
			// 구글 회원가입
			service.joinMemberByGoogle(vo);

			// 구글 로그인
			returnVO = service.loginMemberByGoogle(vo);
			session.setAttribute("id", returnVO.getMemberId());
			// 구글 로그인 이후 마이페이지 접근 안되는 문제 해결 위해 추가
			session.setAttribute("login", returnVO);
			rttr.addFlashAttribute("mvo", returnVO);

		}

		return "redirect:/index"; // 로그인 완료 후 페이지
	}

	// ******************* 로그인 **************************

	// 로그인 폼을 띄우는 부분
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginForm() {
		return "/main/login";
	}

	// 로그인 처리하는 부분
	@RequestMapping(value = "/loginProcess")
	public String loginProcess(HttpSession session, MemberVO dto, HttpServletResponse response, Model model) {

		String returnURL = "";
		if (session.getAttribute("login") != null) {
			// 기존에 login이란 세션 값이 존재한다면
			session.removeAttribute("login"); // 기존값을 제거해 준다.
		}
		// 로그인이 성공하면 memberVO 객체를 반환함.
		MemberVO vo = service.login(dto);

		if (vo != null) { // 로그인 성공
			session.setAttribute("login", vo); // 세션에 login이란 이름으로 MemberVO 객체를 저장해 놈.

			session.setAttribute("loginId", vo.getMemberId());
			System.out.println("로그인 회원정보" + session.getAttribute("login"));
			returnURL = "redirect:/index"; // 로그인 성공시 게시글 목록페이지로 바로 이동하도록 하고

			/*
			 * 자동로그인 1. 로그인이 성공하면, 그 다음으로 로그인 폼에서 쿠키가 체크된 상태로 로그인 요청이 왔는지를 확인한다.
			 */
			if (dto.getIsUseCookie() != null) { // dto 클래스 안에 useCookie 항목에 폼에서 넘어온 쿠키사용 여부(true/false)가 들어있을 것임
				// 쿠키 사용한다는게 체크되어 있으면...
				// 쿠키를 생성하고 현재 로그인되어 있을 때 생성되었던 세션의 id를 쿠키에 저장한다.
				Cookie cookie = new Cookie("loginCookie", session.getId());
				System.out.println("세션아이디 : " + session.getId()); // 세션아이디 체크
				// 쿠키를 찾을 경로를 컨텍스트 경로로 변경해 주고...
				cookie.setPath("/");
				int amount = 60 * 60 * 24 * 7; // 단위는 (초)임으로 7일정도로 유효시간을 설정해 준다.
				cookie.setMaxAge(amount);
				// 쿠키를 적용해 준다.
				response.addCookie(cookie);
				Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount));
				service.keepLogin(vo.getMemberId(), session.getId(), sessionLimit);
			}

		} else { // 로그인에 실패한 경우
			returnURL = "redirect:/login"; // 로그인 폼으로 다시 가도록 함
		}

		model.addAttribute("member", vo);
		if (vo.getRole() == 1) {
			return "redirect:/admin_page";
		} else {
			return returnURL; // 위에서 설정한 returnURL 을 반환해서 이동시킴
		}

	}

	// 예진 회원가입시 로그인 체크
	@ResponseBody
	@RequestMapping(value = "/loginChk")
	public int loginChk(MemberVO vo) throws Exception {
		int result = service.loginChk(vo);
		System.out.println("로그인 체크   " + result);
		return result;
	}

	// ******************* 로그아웃 **************************
	// 로그아웃 하는 부분
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Object obj = session.getAttribute("login");
		System.out.println("로그아웃 프로세스 이전 : session.getAttribute(\"login\")  :  " + session.getAttribute("login"));

		if (obj != null) {
			MemberVO vo = (MemberVO) obj;
			// null이 아닐 경우 제거
			session.removeAttribute("login");
			System.out.println(session.getAttribute("login"));
			session.invalidate(); // 세션 전체를 날려버림

			// 쿠키를 가져와보고
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			// 구글 로그인 시 쿠키 남아 오류 남는것 수정 ( if -> while)
			if (loginCookie != null) {
				if (loginCookie.getMaxAge() != 0) {

					// null이 아니면 존재하면!
					loginCookie.setPath("/");
					// 쿠키는 없앨 때 유효시간을 0으로 설정하는 것 !!! invalidate같은거 없음.
					loginCookie.setMaxAge(0);
					// 쿠키 설정을 적용한다.
					response.addCookie(loginCookie);

					// 사용자 테이블에서도 유효기간을 현재시간으로 다시 세팅해줘야함.
					Date date = new Date(System.currentTimeMillis());
					service.keepLogin(vo.getMemberId(), session.getId(), date);

				}
			}
		}

		return "redirect:/login"; // 로그아웃 후 로그인화면으로 이동하도록...함
	}

	// ******************* 회원가입 **************************
	@Autowired
	private UserService UserService;

	@GetMapping("/join")
	public String joinView() {
		return "/main/join";
	}

	@PostMapping("/joinProcess")
	public String join(MemberVO vo) throws Exception {

		// 예진 회원가입시 아이디 중복 확인
		// 예진 회원가입시 아이디 중복 확인 & 닉네임 중복 확인

		l.info("아이디 중복확인 부분 ");

		int result = service.idChk(vo);
		int nickresult = service.nicknameChk(vo);
		try {
			if (result == 1 || nickresult == 1) {
				System.out.println("중복확인(UserController) : " + vo);
				System.out.println("result: " + result + "   nickresult:  " + nickresult);
				return "/main/join"; // 아이디 중복 or 닉네임 중복 - > 다시 회원가입 페이지로 이동
			} else if (result == 0 & nickresult == 0) {
				System.out.println("joinprocess 에서 중복이 아닐 때 vo : " + vo);
				System.out.println("result: " + result + "   nickresult:  " + nickresult);
				service.join(vo); // 중복 아니면 회원가입 진행
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}

		// 예진 회원가입 primaryKey 뜨며 안되는 문제 수정
		return "redirect:/login";// 회원가입 로그인 화면으로
	}

	// ***********************중복확인***************************

	// 예진 회원가입시 아이디 중복확인
	@ResponseBody
	@RequestMapping(value = "/idChk")
	public int idChk(MemberVO vo) throws Exception {
		int result = service.idChk(vo);
		System.out.println("아이디 중복확인   " + result);
		System.out.println("UserController 에서 아이디 중복확인 vo 값 : " + vo);
		return result;
	}

	// 예진 회원가입시 닉네임 중복확인
	@ResponseBody
	@RequestMapping(value = "/nicknameChk")
	public int nicknameChk(MemberVO vo) throws Exception {
		int result = service.nicknameChk(vo);
		System.out.println("닉네임 중복확인   " + result);
		System.out.println("UserController 에서 닉네임 중복확인 vo 값 : " + vo);
		return result;
	}

	// ******************* mypage - user **************************

	// 개인정보수정 페이지로 이동
	@RequestMapping(value = "profile_update", method = RequestMethod.GET)
	public String myPageUpDate(Model model, HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("login");
		model.addAttribute("member", member);

		return "/mypage/profile_update";
	}

	// 개인정보 수정하기 - 진형
	@PostMapping("/profileUpdate")
	public String profileUpdate(MemberVO vo, HttpSession session, Model model, HttpServletRequest request)
			throws Exception {
		MemberVO loginMember = (MemberVO) session.getAttribute("login");
		vo.setMemberId(loginMember.getMemberId());

		// 파일 업로드 처리 - 진형
		MultipartFile uploadFile = vo.getUploadFile();

		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "/upload/";

		if (!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File(root_path + attach_path + fileName));
			vo.setFileName(fileName);
			vo.setFilePath(root_path + attach_path);
			vo.setFullFilePath(attach_path + fileName);
		}
		UserService.profileUpdate(vo);
		loginMember.setFullFilePath(vo.getFullFilePath());
		loginMember.setNickname(vo.getNickname());

		System.out.printf("update : " + vo); // 업데이트된 정보
		model.addAttribute("member", vo);
		return "redirect:/profile_show"; // 회원정보수정후 마이페이지로
	}

	// 비밀번호 수정하기
	@PostMapping("/pwdUpdate")
	public String pwdUpdate(MemberVO vo, HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("login"); // 저장한 vo를 loginMember에 담음
		if (vo.getPassword().equals(loginMember.getPassword())) {
			vo.setMemberId(loginMember.getMemberId()); // db에 vo에 세션에서 넘겨받은 아이디를 넣어서 변경
			UserService.pwdUpdate(vo); // 비밀번호 변경 쿼리 실행해서 세션에 비밀번호가 체인지
			System.out.println("pwdchange : " + vo.getPassword()); // 변경된 비밀번호 정보

		} else {
			return "redirect:/profile_update";
		}

		session.removeAttribute("login");
		session.invalidate(); // 세션 전체를 날려버림
		return "redirect:/success/pwd_change"; // 비밀번호 변경후 success로
	}

	// 계정 삭제하기
	@PostMapping("/memberDelete")
	public String memberDelete(MemberVO vo, HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("login"); // 저장한 vo를 loginMember에 담음
		vo.setMemberId(loginMember.getMemberId()); // db에 vo에 세션에서 넘겨받은 아이디를 넣어서 변경
		System.out.println(vo.getPassword() + " : DB 저장된 비밀번호");
		System.out.println(loginMember.getPassword() + " : 입력한 세션 비밀번호");

		if (vo.getPassword().equals(loginMember.getPassword())) {
			System.out.println("회원탈퇴성공---------");
			UserService.memberDelete(vo);
			session.removeAttribute("login");
			session.invalidate(); // 세션 전체를 날려버림
			return "redirect:/login"; // 계정 삭제후 login으로
		} else {
			System.out.println("탈퇴실패=============");
			return "redirect:/success/delete";
		}

	}

	// 내 프로필 보기
	@RequestMapping(value = "/profile_show")
	public String profile_show(MemberVO vo, Model model, HttpSession session) {
		MemberVO LoginInfo = (MemberVO) session.getAttribute("login");

		// 예진 구글 로그인 했을 경우
		if (service.loginMemberByGoogle(vo) != null) { // 구글 로그인 한 값이 존재하면
			LoginInfo = service.loginMemberByGoogle(vo);
		}

		vo.setMemberId(LoginInfo.getMemberId());
		model.addAttribute("member", UserService.LoginInfo(vo));
		return "/mypage/profile_show";
	}

	// 다른 회원의 프로필 보기
	@RequestMapping(value = "/profile_other")
	public String profile_other() {
		return "/mypage/profile_other";
	}

	// 팔로우 리스트 보기
	@RequestMapping(value = "/follow_list")
	public String follow_list() {
		return "/mypage/follow_list";
	}

	// 문의 게시판
	@RequestMapping(value = "/question")
	public String question() {
		return "/mypage/question";
	}

	// ******************* success **************************

	// 삭제 성공
	@RequestMapping(value = "/success/delete")
	public String success_delete() throws Exception {
		return "/success/delete";
	}

	// 비밀번호 변경 성공
	@RequestMapping(value = "/success/pwd_change")
	public String success_pwd_change() throws Exception {
		return "/success/pwd_change";
	}

	// 개인정보 수정 성공
	@RequestMapping(value = "/success/update")
	public String success_update() throws Exception {
		return "/success/update";
	}

	/** 진형 **/
	/* 비밀번호 찾기 */
	@RequestMapping(value = "/success/findpw", method = RequestMethod.GET)
	public void findPwGET() throws Exception {

	}

	// 비밀번호찾기
	@ResponseBody // aop 뷰리졸버를 타지않고 내가 쓴대로 내보낸다. json이나 ajax쓸때 거의 적어줘야함
	@RequestMapping(value = "/success/findpw", method = RequestMethod.POST)
	public String findPwPOST(@ModelAttribute MemberVO member) throws Exception {
		return service.findPw(member);
	}

	// ******************* 문의 등록하기 **************************

	// 진형 - 문의 등록하기
	@PostMapping("questionProcess")
	public String question(QueVO vo, HttpServletRequest request) throws Exception {

		UserService.question(vo, request);
		return "redirect:/selectMyQuestion";
	}

	// 내가 등록한 문의 페이지 go
	@RequestMapping(value = "/myquestion_list")
	public String myquestion_list() {
		return "/mypage/myquestion_list";
	}

	// 내가 등록한 문의 리스트뽑아오기
	@RequestMapping("/selectMyQuestion")
	public String selectMyQuestion(QueVO vo, HttpSession session, Model model) {

		MemberVO loginInfo = (MemberVO) session.getAttribute("login"); // 로그인 정보 뽑아오기
		String writer = loginInfo.getMemberId(); // writer라는 변수에 로그인 아이디 저장

		model.addAttribute("selectMyQuestion", UserService.selectMyQuestion(writer));

		return "mypage/myquestion_list";
	}

	// 진형 - 다른 사람 프로필 조회
	@RequestMapping("/selectOtherProfile")
	public String selectOtherProfile(@RequestParam("memberId") String memberId, Model model) {
		MemberVO vo = UserService.selectOtherProfile(memberId);
		model.addAttribute("OtherProfile", vo);

		return "/mypage/profile_other";
	}

}