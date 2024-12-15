package com.kh.mybatis.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.mybatis.board.model.service.BoardServiceImpl;
import com.kh.mybatis.board.model.vo.Attachment;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.MyRenamePolicy;
import com.oreilly.servlet.MultipartRequest;


@WebServlet("/insert.board")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public BoardInsertController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) 인코딩 설정(UTF-8)
		request.setCharacterEncoding("UTF-8");
		
		// 2) 값 뽑기
//		String userNo = request.getParameter("userNo");
//		System.out.println(userNo); // null
		
		// form태그 요청 시 multipart/form-data 형식으로 전송하는 경우
		// request.getParameter() 로 값 뽑기 불가능
		// com.oreilly.servlet => cos
		
		// step 0) 요청이 multipart방식으로 잘 전송이 되었는지 확인
		if(ServletFileUpload.isMultipartContent(request)) {
			// step 1) 전송되는 파일 처리를 위한 작업
			// 1_1. 전송파일 용량제한 10MB => Byte단위로 작성해야함
			int maxSize = 10485760; // 1024 * 1024 * 10;
			
			// 1_2. 전달된 파일을 저장할 서버의 폴더 경로 알아내기
			// getRealPath() => DB 컬럼에는 파일 경로 + 파일 명 insert
			// HttpServletRequest => 
			// HttpSession => jsp, servlet, 등등 일반적인 클래스에서 사용가능
			// ServletContext => 이놈이 모든 파일에 접근 가능하다...
			// ServletContext.getRealPath() 
			// => 인자값으로 webapp부터 board_upfiles폴더까지의 경로를 문자열로 전달 
			
			HttpSession session = request.getSession();
			ServletContext application = session.getServletContext();
			String savePath = application.getRealPath("/resources/board_upfiles");
			
			// step 2) 서버에 업로드
			// a.jpg => cos에서 넘버링하도록 만들어둠 a2.jpg a3.jpg
			// kakao 규칙
			// kakaoTalk_ 20230712_ 183458 800
			
			/*
			 * - HttpServletRequest request
			 *   => MultipartRequest multiRequest객체로 변환
			 *   
			 *   [ 표현법 ]
			 *   
			 *   MultipartRequest multipartRequest = 
			 *   new MultipartRequest(request, savePath, maxSize, Encoding, 파일명을 수정해주는 객체);
			 *   - request객체, 저장경로, 업로드최대용량, 인코딩방식, 파일명을 수정해주는 객체 File => rename()
			 *   
			 *   - MultipartRequest객체를 생성하면, 생성 시점에 파일이 업로드됨!
			 *   - 사용자가 올린 파일명은 해당 폴더에 업로드하지 않는 것이 일반적 !
			 *   
			 *   Q) 파일명을 수정하는 이유?
			 *   A) 같은 파일명이 존재할 수 있기때문
			 *      파일명에 한글 / 특수문자 / 공백문자 포함된 경우 서버에 따라 문제가 일어날 수 있음
			 *   
			 *   cor.jar => 기본적으로 파일명을 수정해주는 객체
			 *   => 내부적으로 rename()호출하면서 파일명 수정
			 *   => bono.jpg bono1.jpg bono2.jpg
			 *   
			 *   => 우리 입맛대로 파일명을 수정해서 겹치짖 않게 하기 위해
			 *   	MyRenamePolicy 라는 클래스를 만들었음
			 */
			
			// 1) 값 이관
			MultipartRequest multiRequest = 
					new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyRenamePolicy());
			
			// ---- 파일 업로드 ----
			
			// BOARD TABLE 게시글 정보 입력 
			// 2) 값 뽑기
			String userNo = multiRequest.getParameter("userNo");
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String categoryNo = multiRequest.getParameter("category");
			
			// 3) VO객체로 가공
			Board board = new Board();
			board.setBoardTitle(title);
			board.setBoardContent(content);
			board.setBoardWriter(userNo);
			board.setCategory(categoryNo);
			
			// 3_2) 첨부파일의 경우 선택적
			Attachment attachment = null;
			
			// 첨부파일 유무 확인
			// multiRequest.getOriginalFileName("키값"); // HTML문서상 input 타입 file의 name속성값
			// 첨부파일이 존재한다면 "원본파일명" // 존재하지 않는다면 null값을 반환
			if(multiRequest.getOriginalFileName("upfile") != null) {
				
				// 첨부파일 존재 ! => VO객체로 가공
				attachment = new Attachment();
				
				// originName
				attachment.setOriginName(multiRequest.getOriginalFileName("upfile"));
				
				// changeName
				attachment.setChangeName(multiRequest.getFilesystemName("upfile"));
				
				// 경로 지정
				attachment.setFilePath("/resources/board_upfiles");
				
			}
			
			// 4) 서비스호출
			int result = new BoardServiceImpl().insertBoard(board, attachment);
			
			// 5) 응답화면 지정
			if(result > 0) {
				
				request.getSession().setAttribute("alertMsg", "🎆게시글 등록 성공~🎆");
				
				// 이거 하면 안됨? ㅇㅇ 안됨 ㅋㅋ
				// 데이터가 없어서 화면상에 목록이 나오지 않음
				// => 데이터를 조회해서 뿌려줄 값들이 아래 dispatcher에는 담겨있지 않음
//				request.getRequestDispatcher("/WEB-INF/views/board/board_list.jps").forward(request, response);
				
				// url에 currentPage를 작성하지 않으면 어떤 페이지를 불러야하는지 모르기때문에 값이 없음
				// 현재 Servlet과 매핑된 URL요청에서 수행할 작업을 완료하고
				// redirect를 사용하는 이유는 작업을 완료하고 새로운 URL에 대한 요청을 수행하기위해 사용
				
				// => Servlet매핑값으로 호출된 서블릿이 작업을 완료하면 
				// redirect를 통해 작성된 URL에 대한 요청에 응답하기 위함
				response.sendRedirect(request.getContextPath() + "/list.board?currentPage=1");
				
			} else {
				
				// 실패했을 경우 이미 업로드한 파일을 삭제
				if(attachment != null) {
					new File(savePath + "/" + attachment.getChangeName()).delete();
				}
				
				request.setAttribute("failMsg", "게시글 작성 실패");
				request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp").forward(request, response);
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
