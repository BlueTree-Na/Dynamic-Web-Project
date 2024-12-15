package com.kh.mybatis.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.board.model.service.BoardService;
import com.kh.mybatis.board.model.service.BoardServiceImpl;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.PageInfo;
import com.kh.mybatis.common.Pagination;

@WebServlet("/search.board")
public class BoardSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardSearchController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 사용자가 선택한 옵션과 사용자가 입력한 키워드를 가지고
		// 페이징처리가 끝난 검색결과를 들고 갈것
		
		String condition = request.getParameter("condition");
		// "writer" / "title" / "content"
		String keyword = request.getParameter("keyword");
		// 사용자가 입력한 값
		
		// 최선 => DTO (Data Transfer Object)
		// 차선 => Map (Collection)
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		
		BoardService boardService = new BoardServiceImpl();
		
		int count = boardService.searchedCount(map);
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int boardLimit = 3; 
		int pageLimit = 10;
		
		PageInfo pi = Pagination.getPageInfo(count, currentPage, boardLimit, pageLimit);
		
		List<Board> list = boardService.selectSearchList(pi, map);
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.setAttribute("keyword", keyword);
		request.setAttribute("condition", condition);
		
		request.getRequestDispatcher("/WEB-INF/views/board/board_list.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
