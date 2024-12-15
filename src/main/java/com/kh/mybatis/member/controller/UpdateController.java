package com.kh.mybatis.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mybatis.member.model.service.MemberServiceImpl;
import com.kh.mybatis.member.model.vo.Member;

@WebServlet("/update.me")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) POST 방식 => 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) request 값 뽑기
		// userId, userName, email, interest(checkbox)
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String[] interestArr = request.getParameterValues("interest");
		String interest = interestArr != null ? String.join(",", interestArr) : "" ;
		
		// 3) VO객체로 가공
		Member member = new Member();
		member.setUserId(userId);
		member.setUserName(userName);
		member.setEmail(email);
		member.setInterest(interest);
		
		// 4) 요청 처리 : Service 메소드 호출
		int result = new MemberServiceImpl().update(member);
		
		// 5) 처리된 결과값에 따라 응답화면 지정
		if(result > 0) {
			
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "🎉 회원정보 수정에 성공했습니다! 🎉");
			
			// 문제 발생
			// Update에 성공했지만, session의 loginUser키 값에는 
			// 로그인 당시 조회했던 값들이 필드에 담겨있기 때문에 마이페이지에서 갱신되기 전 값들이 출력됨
			
			// 목표 => 갱신된 회원정보를 얻어야함
			// 현재 update에 성공한 행을 식별할 수 있는 값이 존재?? => userId
			// (Member)session.getAttribute("loginUser").get머시기();
			// => 안됨... 참조 연산자 우선순위를 낮추기 위해 Member Type으로 변환 후 괄호로 묶어주면 됨!!
			String userPwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
			
			Member selectMember = new Member();
			selectMember.setUserId(userId);
			selectMember.setUserPwd(userPwd);
			
			Member updateMember = new MemberServiceImpl().login(selectMember);
			
			session.setAttribute("loginUser", updateMember);
			
			// 응답화면 지정 방법 2가지 중 뭘 선택해야 좋을까?
			// 1. sedRedirect
			// 2. forward
			
			// 1. 이 방법이 더 좋은듯?
			// 유지보수를 한다고 가정 했을 때,
			// 이미 응답할 수 있는 서블릿(매핑된 것)을 사용함으로써 페이지를 찾아가는 것을 새로 만들어 내지 말고
			// 만들어진 ContextPath 경로 + 매핑 값으로 처리해서
			// 이미 만들어진 서블릿에 요청을 넘겨버림!
			response.sendRedirect(request.getContextPath() + "/myPage.me");
			
			// 2.
//					request.getRequestDispatcher("/WEB-INF/views/member/my_page.jsp").forward(request, response);
			
		} else {
			request.setAttribute("failMsg", "😥 회원정보수정에 실패했습니다. 😥");
			request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp").forward(request, response);
		}
	}

}
