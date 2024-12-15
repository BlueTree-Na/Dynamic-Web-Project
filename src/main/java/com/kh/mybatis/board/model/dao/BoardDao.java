package com.kh.mybatis.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.vo.Attachment;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.board.model.vo.Category;
import com.kh.mybatis.board.model.vo.Reply;
import com.kh.mybatis.common.PageInfo;

public class BoardDao {
	
	public int selectListCount(SqlSession sqlSession) {
		
		return sqlSession.selectOne("boardMapper.selectListCount");
	}
	
	public List<Board> selectList(SqlSession sqlSession, PageInfo pi){
		
		// 마이바티스에서는 페이징처리를 위해 RowBounds라는 클래스 제공
		
		// * limit : ??
		// * offset : 몇 개의 게시글을 건너 뛰고 조회할 것인지
		/*
		 * boardLimit이 8 일경우
		 * 								offset
		 * currentPage : 1 -> 1 ~ 8        0
		 * currentPage : 2 -> 9 ~ 16       8
		 * 
		 */
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		// 오버로딩된 메소드의 매개변수가 3개짜리임 => 자리 맞춰주기위해 null 전달함
		return sqlSession.selectList("boardMapper.selectList", null, rowBounds);
	}
	
	public int increaseCount(SqlSession sqlSession, int boardNo) {
		return sqlSession.update("boardMapper.increaseCount", boardNo);
	}
	
	public Board selectBoard(SqlSession sqlSession, int boardNo) {
//		return sqlSession.selectOne("boardMapper.selectBoard", boardNo);
		return sqlSession.selectOne("boardMapper.selectBoardAndReply", boardNo);
	}
	
	public List<Reply> selectReplyList(SqlSession sqlSession, int boardNo){
		return sqlSession.selectList("boardMapper.selectReplyList", boardNo);
	}
	
	public int searchedCount(SqlSession sqlSession, Map<String, String> map) {
		return sqlSession.selectOne("boardMapper.searchedCount", map);
	}
	
	public List<Board> selectSearchList(SqlSession sqlSession, Map<String, String> map, RowBounds rowBounds) {
		return sqlSession.selectList("boardMapper.selectSearchList", map, rowBounds);
	}
	
	public List<Category> selectCategory(SqlSession sqlSession){
		return sqlSession.selectList("boardMapper.selectCategory");
	}
	
	public int insertBoard(SqlSession sqlSession, Board board) {
		return sqlSession.insert("boardMapper.insertBoard", board);
	}
	
	public int insertAttachment(SqlSession sqlSession, Attachment attachment) {
		return sqlSession.insert("boardMapper.insertAttachment", attachment);
	}
	
	public Board findById(SqlSession sqlSession, int boardNo) {
		Board board = sqlSession.selectOne("boardMapper.findById", boardNo);
		System.out.println(board + " 여기 DAO");
		return sqlSession.selectOne("boardMapper.findById", boardNo);
	}
	
	public Attachment selectAttachment(SqlSession sqlSession, int boardNo) {
		return sqlSession.selectOne("boardMapper.selectAttachment", boardNo);
	}
	
}


















