package com.kh.mybatis.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mybatis.member.model.service.MemberServiceImpl;
import com.kh.mybatis.member.model.vo.Member;

@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * <HttpServletRequest, HttpServletResponse>
		 * 
		 * - request : 서버로 요청할 때 정보(요청 시 전달 값, 요청 전송 방식, 요청한 사용자 정보 등등)
		 * - response : 요청에 응답하고자 할 때 사용하는 객체
		 */
		
		// 절차
		// 1) GET / POST => 요청방식이 POST라면 인코딩 작업
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값의 여부 => 있다면 데이터 가공
		// request.getParameter("키값") : String
		// request.getParameterValues("키값") : String[] => checkbox일 경우 사용
		
		String userId = request.getParameter("member-id");
		String userPwd = request.getParameter("member-pwd");
		
		// 3) 멤버 객체 생성
		Member member = new Member();
		
		// 데이터 가공처리 (담기)
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		
		// 해당 요청을 처리해주는 서비스의 메소드를 호출 (service)
		Member loginUser = new MemberServiceImpl().login(member);
		
		// 모든 조건을 만족하는 행이 존재한다면 반환된 값에는 필드값이 회원정보로 꽉찬 주소값
		// 하나의 조건이라도 만족하지 못했다면 null값
		// System.out.println(loginUser);  // 콘솔창 테스트용
		
		// 4) 처리된 결과를 사용자가 보게 될 응답화면 지정
		// 스텝1. request객체 응답화면에 보여질 값 담기 → setAttribute()
		// 		 request에 담으면 jsp에서만 사용가능함. 다른페이지에서는 못씀. 그래서 로그인을 여기다가 담으면 다른페이지에서 못써
		//
		// 스텝2. RequestDispatcher 객체 생성 → 뷰 지정
		// 스텝3. RequestDispatcher 객체로부터 forward() 호출
		
		// 1. 어딘가에 응답화면에 보여질 값 담기 (request, session, application, page)
		
		/*
		 * 1) application : 웹 애플리케이션 전역에서 언제나 꺼내 쓸 수 있음 (일반 자바클래스에서 뽑아 쓸 수 있음)
		 *
		 * 2) session : 모든 JSP와 Servlet에서 꺼내 쓸 수 있음
		 * 				단, 직접적으로 session에 담은 값을 지우기 전까지만
		 * 				세션이 끊기는 경우: 브라우저 종료, 서버 멈춤, 코드로 지웠다
		 *
		 * 3) request: 해당 request를 포워딩한 응답 JSP에서만 쓸 수 있음 (요청1 = 응답1 이후 소멸)
		 * 				요청부터 응답페이지까지만 딱 쓸 수 있음
		 *
		 * 4) page: 담은 값을 해당 JSP페이지에서만 쓸 수 있음
		 *
		 * 작다
		 *
		 * → sesison, request를 많이 사용함
		 *
		 * → 공통적으로 데이터를 담고자 할 떄: xxx.setAttribute(키, 밸류);
		 * → 		  데이터를 뽑고자 할 떄: xxx.getAttribute(키);
		 * → 		  데이터를 지우고자 할 떄: xxx.removeAttribute(키);
		 *
		 * 예시)
		 * 로그인 시 : session.setAttribute("userInfo", loginUser);
		 * 로그아웃 시 : session.removeAttribute("userInfo");
		 */
		
		// 2. RequsetDispatcher객체 생성(응답할 뷰 지정) → forward();
		// 로그인에 실패할 수 도 있음 / 성공할 수 도 있음
		
		// 케이스가 2개니까 조건문 if 사용
		if(loginUser != null) {
			// 성공
			
			// 사용자의 정보 넘기기
			
			// 로그인한 회원의 정보를 로그아웃하거나
			// 브라우저를 종료하기 전까지는 계속 사용할 예정이기 때문에
			// session에 담기
			
			// step1. session의 attribute에 사용자 정보 담기
			// session 객체의 Type : HttpSession => session의 타입이 굉장히 많다. 지금은 HttpSession 사용
			// => 현재 요청 보낸 Client의 Session : request.getSession();
			
			// Session으로 사용하기 위해 가공이 필요한듯?
			HttpSession session = request.getSession(); 
			session.setAttribute("loginUser", loginUser);
			
			// * 포워딩
			/*
			// step2. RequestDispatcher객체 생성
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			
			// step3. forward(); 전달을 위한 메소드 호출
			view.forward(request, response);
			*/
			
			// localhost/super
			// sendRedirect : Client에게 url을 다시 요청하게 해줌
			// response.sendRedirect("/재요청경로");
			
			session.setAttribute("alertMsg", "🎊 로그인에 성공했습니다~ 🎊");
			response.sendRedirect(request.getContextPath());
			
		} else {
			// 실패
			request.setAttribute("failMsg", "로그인에 실패했습니다");
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp");
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
