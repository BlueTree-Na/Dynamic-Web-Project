package com.kh.mybatis.member.model.service;


import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.common.Template;
import com.kh.mybatis.member.model.dao.MemberDao;
import com.kh.mybatis.member.model.vo.Member;
import com.kh.mybatis.member.model.vo.MemberDto;

public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao = new MemberDao();
	
	@Override
	public int insertMember(Member member) {
		
		// Connection 역할 대신하는 SqlSession
		SqlSession sqlSession = Template.getSqlSession();
		
		int result = memberDao.insertMember(sqlSession, member);
		
		if(result > 0) sqlSession.commit();
		
		sqlSession.close();
		
		return result;
	}
	
	@Override
	public Member login(Member member) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		Member loginUser = memberDao.login(sqlSession, member);

		sqlSession.close();
		
		return loginUser;
	}

	@Override
	public int update(Member member) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int result = memberDao.update(sqlSession, member);
		
		if(result > 0) sqlSession.commit();
		
		sqlSession.close();
		
		return result;
	}

	@Override
	public int updatePwd(MemberDto memberDto) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int result = memberDao.updatePwd(sqlSession, memberDto);
		
		if(result > 0) sqlSession.commit();
		
		sqlSession.close();
		
		return result;
	}

	@Override
	public int delete(MemberDto memberDto) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int result = memberDao.delete(sqlSession, memberDto);
		
		if(result > 0) sqlSession.commit();
		
		sqlSession.close();
		
		return result;
	}

	@Override
	public int checkId(String userId) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int count = memberDao.checkId(sqlSession, userId);
		
		sqlSession.close();
		
		return count;
	}
	
	
}
