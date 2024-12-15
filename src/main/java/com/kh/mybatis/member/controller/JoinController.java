package com.kh.mybatis.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.member.model.service.MemberServiceImpl;
import com.kh.mybatis.member.model.vo.Member;

@WebServlet("/sign-up.me")
public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public JoinController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 뭐할건데~ POST
		// 1) 인코딩 설정
		// 이거 어쩔거야 중복인데 뺄거야 말거야 빼면 스태틱인데 한줄이라 빼기도 애매해 ㅡㅡ
		request.setCharacterEncoding("UTF-8"); 
		
		// 회원가입 기능
		// 사용자가 회원가입 양식에 작성한 값을 MEMBER 테이블까지 전달 후 한행 INSERT
		// 2) request 객체로부터 요청 시 전달값 뽑기
		String userId = request.getParameter("userId"); // "필수입력"
		String userPwd = request.getParameter("userPwd"); // "필수입력"
		String userName = request.getParameter("userName"); // "필수입력"
		String email = request.getParameter("email"); // "필수입력"
		String[] interestArr = request.getParameterValues("interest");
		String interest = interestArr != null ? String.join(",", interestArr) : "" ;
		
		// 3) Member 객체에 담기 : setter
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		member.setUserName(userName);
		member.setEmail(email);
		member.setInterest(interest);
		
		// 4) 요청처리(Service호출)
		int result = new MemberServiceImpl().insertMember(member);
		
		// 5) 처리결과를 가지고 사용자가 보게될 응답화면 지정
		if(result > 0) { // 성공 => index.jsp로 sendRedirect()
			request.getSession().setAttribute("alertMsg", "🎊 회원가입에 성공했습니다~ 🎊");
			response.sendRedirect(request.getContextPath());
		} else { // 실패 => 실패 페이지로 포워딩
			request.setAttribute("failMsg", "회원가입에 실패했습니다...");
			request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
