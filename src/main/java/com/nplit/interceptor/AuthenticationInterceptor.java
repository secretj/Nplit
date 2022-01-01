package com.nplit.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.nplit.service.UserService;
import com.nplit.vo.MemberVO;

//�α���ó���� ����ϴ� ���ͼ���
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Inject
	UserService service;

	// preHandle() : ��Ʈ�ѷ����� ���� ����Ǵ� �޼���
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// session ��ü�� ������
		HttpSession session = request.getSession();
		// loginó���� ����ϴ� ����� ������ ��� �ִ� ��ü�� ������
		Object obj = session.getAttribute("login");
		if (obj == null) { // �α��ε� ������ ���� ���...
			// �츮�� ����� �� ��Ű�� �����´�.
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if (loginCookie != null) {
				// ��Ű�� �����ϴ� ���(������ �α��ζ� ������ ��Ű�� �����Ѵٴ� ��)
				// loginCookie�� ���� �������� -> ��, �����س� ����Id�� ��������
				String sessionId = loginCookie.getValue();
				System.out.println("11111111111111" + sessionId);
				System.out.println(sessionId);

				// ����Id�� checkUserWithSessionKey�� ������ ������ �α��������� �ִ��� üũ�ϴ�
				// �޼��带 ���ļ�
				// ��ȿ�ð��� > now() �� �� ���� ��ȿ�ð��� ������ �����鼭 �ش� sessionId ������
				// ������ �ִ� ����� ������ ��ȯ�Ѵ�.
				MemberVO memberVO = service.checkUserWithSessionKey(sessionId);
				System.out.println(memberVO.toString());
				if (memberVO != null) { // �׷� ����ڰ� �ִٸ�
					// ������ �������� �ش�.
					session.setAttribute("login", memberVO);
					return true;
				}
			}

			// ���� �Ʒ��� �α��ε� �ȵ��ְ� ��Ű�� �������� �ʴ� ���ϱ� �ٽ� �α��� ������ ����������
			// �ȴ�.
			// �α����� �ȵǾ� �ִ� ���������� �α��� ������ �ٽ� ��������(redirect)
			response.sendRedirect("/login");
			return false; // ���̻� ��Ʈ�ѷ� ��û���� ���� �ʵ��� false�� ��ȯ��
		}

		// preHandle�� return�� ��Ʈ�ѷ� ��û uri�� ���� �ǳ� �ȵǳĸ� �㰡�ϴ� �ǹ���
		// ���� true���ϸ� ��Ʈ�ѷ� uri�� ���� ��.
		return true;
	}

	/*
	 * ��Ʈ�ѷ��� ����ǰ� ȭ���� �������� ������ ����Ǵ� �޼���
	 * 
	 * @Override public void postHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object handler, ModelAndView modelAndView)
	 * throws Exception {
	 * 
	 * //super.postHandle(request, response, handler, modelAndView); }
	 */

}