package com.kh.mybatis.member.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.vo.Member;
import com.kh.mybatis.member.model.vo.MemberDto;

public class MemberDao {
	
	public int insertMember(SqlSession sqlSession, Member member) {
		
		/* ------ MyBatis ------ */
		/*
		 * SqlSession이 제공하는 메소드를 통해 SQL문을 찾아서 실행하고 결과를 받아볼 수 있음
		 * sqlSession 객체에 insert() / update() / delete() 메소드들이 존재
		 * 
		 * insert("SQL문", SQL문 실행 시 필요한 데이터);
		 * SQL문 => namespace.id
		 * SQL문 실행 시 필요한 데이터 => 자바타입(풀클래스명) / 식별자명(별칭)
		 */
		
		return sqlSession.insert("memberMapper.insertMember", member);
	}
	
	public Member login(SqlSession sqlSession, Member member) {
		// selectOne / selectList 둘중 하나
		// selectOne() : 조회결과가 존재하지 않는다면 null을 반환 / 존재한다면 생성 및 가공한 객체 반환
		return sqlSession.selectOne("memberMapper.login", member);
	}
	
	public int update(SqlSession sqlSession, Member member) {
		
		return sqlSession.update("memberMapper.update", member);
	}
	
	public int updatePwd(SqlSession sqlSession, MemberDto memberDto) {
		
		return sqlSession.update("memberMapper.updatePwd", memberDto);
	}
	
	public int delete(SqlSession sqlSession, MemberDto memberDto) {
		
		return sqlSession.update("memberMapper.delete", memberDto);
	}
	
	public int checkId(SqlSession sqlSession, String userId) {
		
		return sqlSession.selectOne("memberMapper.checkId", userId);
	}
}
