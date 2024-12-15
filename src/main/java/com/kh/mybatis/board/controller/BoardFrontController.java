package com.kh.mybatis.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.board")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardFrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 요청 uri를 가져옴
		String uri = request.getRequestURI(); 
		
		// 요청온거 잘라서 쓸수 있음~
		String mapping = uri.substring(uri.lastIndexOf("/" + 1, uri.lastIndexOf("."))); 
		
		switch(mapping) {
		case "search" : break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
