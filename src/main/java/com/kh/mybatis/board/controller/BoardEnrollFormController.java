package com.kh.mybatis.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.board.model.service.BoardServiceImpl;
import com.kh.mybatis.board.model.vo.Category;

@WebServlet("/enrollForm.board")
public class BoardEnrollFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public BoardEnrollFormController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 작성 폼 띄울 때
		// 작성 폼 안에 DB에서 조회한 CATEGORY테이블의 값을
		// SELECT태그의 OPTION요소로 만들어줄 것
		
		// if(request.getSession().getAttribute("loginUser") != null)
		
		List<Category> categoryList = new BoardServiceImpl().selectCategory();
		request.setAttribute("categoryList", categoryList);
		
		request.getRequestDispatcher("/WEB-INF/views/board/enroll_form.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
