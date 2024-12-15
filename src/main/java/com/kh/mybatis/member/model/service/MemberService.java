package com.kh.mybatis.member.model.service;

import com.kh.mybatis.member.model.vo.Member;
import com.kh.mybatis.member.model.vo.MemberDto;

public interface MemberService {

	// 회원가입
	int insertMember(Member member);
	
	// 로그인
	Member login(Member member);
	
	// 회원 정보 수정
	int update(Member member);
	
	// 회원 정보 비밀번호 수정
	int updatePwd(MemberDto memberDto);
	
	// 회원 탈퇴
	int delete(MemberDto memberDto);
	
	// 아이디 중복 체크
	int checkId(String userId);
	
}
