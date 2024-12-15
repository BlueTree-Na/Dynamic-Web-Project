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

@WebServlet("/delete.me")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// 현재 로그인한 회원의 정보
		// 방법 1. input type="hidden"
		// 방법 2. session.getAttribute("loginUser");
		HttpSession session = request.getSession();
		
		String userPwd = request.getParameter("userPwd");
		int userNo = ((Member)session.getAttribute("loginUser")).getUserNo();
		
		MemberDto memberDto = new MemberDto();
		memberDto.setUserNo(userNo);
		memberDto.setUserPwd(userPwd);
		
		int result = new MemberServiceImpl().delete(memberDto);
		
		if(result > 0) {
			session.removeAttribute("loginUser");
			session.setAttribute("alertMsg", "회원 탈퇴하셨군요... 안녕히...");
			response.sendRedirect(request.getContextPath());
		} else {
			request.setAttribute("failMsg", "비밀번호를 확인하세요 !!");
			request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
