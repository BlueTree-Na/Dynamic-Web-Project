package com.kh.mybatis.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/join.me")
public class EnrollPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public EnrollPageController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 회원가입 양식 띄워주기
		// 서블릿에서 응답 지정 방식 2가지
		// 1. RequestDispatcher 객체 이용 (forwarding)
		// 	  
		// 2. sendRedirect(URL 재요청 방식)
		//    code의 재사용이 있을 경우 사용 ( 로그인, 로그아웃, ... )
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/member/enroll_form.jsp");
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
