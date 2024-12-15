package com.kh.mybatis.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.board.model.service.BoardService;
import com.kh.mybatis.board.model.service.BoardServiceImpl;
import com.kh.mybatis.board.model.vo.Attachment;
import com.kh.mybatis.board.model.vo.Board;

@WebServlet("/detail.board")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		Board board = new BoardServiceImpl().findById(boardNo);
		
		String path = ""; // 중복코드 있어서
		
		// 게시글 조회에 성공 했다면
		if(board != null) {
			
			// 4_2) ATTACHMENT 테이블 조회
			Attachment attachment = new BoardServiceImpl().selectAttachment(boardNo);
			
			// 조회된 데이터를 Attribute에 담기
			request.setAttribute("board", board);
			request.setAttribute("attachment", attachment);
			// 포워딩
			// request.getRequestDispatcher("WEB-INF/views/board/detail.jsp").forward(request, response);
			path = "board/detail";
			
		} else {
			request.setAttribute("failMsg", "게시글 조회 실패");
			// request.getRequestDispatcher("/webapp/WEB-INF/views/common/fail_page.jsp").forward(request, response);
			path = "common/fail_page";
		}
		
		request.getRequestDispatcher("/WEB-INF/views/" + path + ".jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
