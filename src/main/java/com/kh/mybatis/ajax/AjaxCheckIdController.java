package com.kh.mybatis.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.member.model.service.MemberServiceImpl;

@WebServlet("/checkId.me")
public class AjaxCheckIdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxCheckIdController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET 요청

		// 2) request => getParameter() => 값 get
		String id = request.getParameter("id");
		
		// 3) VO가공 => id 하나지롱~
		
		// 4) Service 호출
		
		// SELECT USER_ID FROM MEMBER WHERE USER_ID = ? => ResultSet 0행
		// SELECT COUNT(*) FROM MEMBER WHERE USER_ID = ? => ResultSet 1행
		// => Java layer에서 다뤄보기위해 DECODE 안썼음~ (네이버식 NNNNN 과 같이)
		
		int count = new MemberServiceImpl().checkId(id);
		
		// 5) 결과 값에 따른 응답화면 지정
		// 중복값이 있을 때 count == 1 => "NNNNN"
		// 중복값이 없을 때 count == 0 => "NNNNY"
		// 삼항 연산자좀 써라 이자시가
		response.getWriter().print(count > 0 ? "NNNNN" : "NNNNY");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
