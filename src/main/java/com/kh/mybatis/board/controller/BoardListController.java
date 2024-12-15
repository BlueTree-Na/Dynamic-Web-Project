package com.kh.mybatis.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.board.model.service.BoardServiceImpl;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.PageInfo;
import com.kh.mybatis.common.Pagination;

@WebServlet("/list.board")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int listCount = new BoardServiceImpl().selectListCount();
		// System.out.println(listCount); // 107
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		int boardLimit = 8;
		int pageLimit = 8;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, boardLimit, pageLimit);
		
		List<Board> list = new BoardServiceImpl().selectList(pi);
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.getRequestDispatcher("/WEB-INF/views/board/board_list.jsp").forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
