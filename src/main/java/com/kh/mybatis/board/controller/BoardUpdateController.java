package com.kh.mybatis.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.mybatis.board.model.service.BoardServiceImpl;
import com.kh.mybatis.board.model.vo.Attachment;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.MyRenamePolicy;
import com.oreilly.servlet.MultipartRequest;


@WebServlet("/update.board")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public BoardUpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) POST => 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) 값뽑기전 => multipart/form-data
		if(ServletFileUpload.isMultipartContent(request)) {
			
			// 파일 업로드
			// 1. 전송파일 용량 제한
			int maxSize = 1024 * 1024 * 10;
			// 2. 파일을 저장할 물리적인 경로
			String savePath = request.getSession().getServletContext()
									 .getRealPath("/resources/board_upfiles");
			
			// MultipartRequest 객체 생성과 동시에 파일 업로드
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8"
																 , new MyRenamePolicy());
			
			// ----- 파일을 서버에 업로드 -----
			
			// BOARD UPDATE 
			// ATTACHMENT UPDATE 할지 말지
			
			// case 1. 첨부파일이 존재하지 않는 경우 =? BOARD UPDATE + AT X
			// case 2. 첨부파일을 첨부, 기존 첨부파일이 존재 => BOARD UPDATE + AT UPDATE
			// case 3. 첨부파일을 첨부, 기존 첨부파일 존재안함 => BOARD UPDATE + AT INSERT
			
			// 값 뽑자~
			// ----- BOARD -----
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			int boardNo = Integer.parseInt(multiRequest.getParameter("boardNo"));
			
			// ----- VO객체(BOARD)로 가공 -----
			Board board = new Board();
			board.setCategory(category);
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			board.setBoardNo(boardNo);
			
			// Attachment 객체 선언만 => 첨부파일이 존재하지 않을 경우도 있기때문
			// 첨부파일이 존재할 경우만 객체생성
			
			Attachment at = null;
			String fileRename = multiRequest.getOriginalFileName("reUpfile");
			if(fileRename != null) {
				
				// 새로운 파일명이 존재한다면 객체 생성후 => 원본파일명, 수정된 파일명, 파일경로 담기
				// originName, changeName, filePath에 값 담기
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("resources/board_upfiles");
				// 여기까지 새롭게 업로드한 첨부파일에 대한 내용
				
				// INSERT / UPDATE
				// INSERT : 이 첨부파일이 어떤 게시글에 달리는건가 ?
				// UPDATE : ATTACHMENT => fileNo PK(식별번호)
				
				if(multiRequest.getParameter("fileNo") != null) {
					
					// 새로운 첨부파일이 존재 + 원본파일도 존재
					// ATTACHMENT => UPDATE => 원본파일번호 필요함
					// 기존 파일이 가지고있던 FileNo를 at에 담을 것
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("fileNo")));
					
					// 기존에 존재하던 첨부파일 삭제
					new File(savePath + "/" + multiRequest.getParameter("changeName")).delete();
				} else {
					// 새로운 첨부파일이 존재 + 원본파일은 존재하지 않음
					// ATTACHMENT => INSERT
					// 어떤 게시글의 첨부 파일인지(REF_BNO)
					at.setRefBno(boardNo);
				}
			}
			
			int result = new BoardServiceImpl().update(board, at);
			
			// 결과에 따른 응답화면 지정
			if(result > 0) {
				request.getSession().setAttribute("alertMsg", "게시글 수정 성공~");
				response.sendRedirect(request.getContextPath() + "/detail.board?boardNo=" + boardNo);
			} else {
				request.setAttribute("failMsg", "게시글 수정 실패");
				request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp")
					   .forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}




















