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
import com.kh.mybatis.member.model.vo.MemberDto;


@WebServlet("/updatePwd.me")
public class UpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdatePwdController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) POST방식 => 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) request로부터 값 뽑기
		//    식별할 수 있는 값 필요 => 보편적으로 PK로 식별 => userNo
		//    1. input type="hidden"으로 받아오기
		//    2. session에서 뽑아오기
		int userNo = Integer.parseInt(request.getParameter("userNo")); 
		String userPwd = request.getParameter("userPwd");
		String updatePwd = request.getParameter("changePwd");
		
		MemberDto memberDto = new MemberDto(userNo, userPwd, updatePwd);
		
		// 3) 가공 X 그냥 넘기기~ 매개변수~ 인자값으로~
		int result = new MemberServiceImpl().updatePwd(memberDto);
		
		HttpSession session = request.getSession();
		
		// 4) 처리 결과값에 따른 응답화면 지정
		if(result > 0) {
			Member loginUser = ((Member)session.getAttribute("loginUser"));
			loginUser.setUserPwd(updatePwd);
		}
		session.setAttribute("alertMsg", result > 0 ? "비밀번호 변경 성공~!" : "비밀번호 변경 실패...!");
		
		// 성공이든 실패든 마이페이지로 응답
		response.sendRedirect(request.getContextPath() + "/myPage.me");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
