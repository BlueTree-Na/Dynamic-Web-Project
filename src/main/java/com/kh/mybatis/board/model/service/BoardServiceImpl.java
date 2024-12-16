package com.kh.mybatis.board.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.dao.BoardDao;
import com.kh.mybatis.board.model.vo.Attachment;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.board.model.vo.Category;
import com.kh.mybatis.board.model.vo.Reply;
import com.kh.mybatis.common.PageInfo;
import com.kh.mybatis.common.Template;

public class BoardServiceImpl implements BoardService {

	private BoardDao boardDao = new BoardDao();
	
	@Override
	public int selectListCount() {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int listCount = boardDao.selectListCount(sqlSession);
		
		sqlSession.close();
		
		return listCount;
	}

	@Override
	public List<Board> selectList(PageInfo pi) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		List<Board> list = boardDao.selectList(sqlSession, pi);
		
		sqlSession.close();
		
		return list;
	}

	@Override
	public int increaseCount(int boardNo) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int result = boardDao.increaseCount(sqlSession, boardNo);
		
		if(result > 0) sqlSession.commit();
		
		sqlSession.close();
		
		return result;
	}

	@Override
	public Board selectBoard(int boardNo) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		Board board = boardDao.selectBoard(sqlSession, boardNo);
		
		sqlSession.close();
		
		return board;
	}

	@Override
	public List<Reply> selectReplyList(int boardNo) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		List<Reply> list = boardDao.selectReplyList(sqlSession, boardNo);
		
		sqlSession.close();
		
		return list;
	}
	
	@Override
	public int searchedCount(Map<String, String> map) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int count = boardDao.searchedCount(sqlSession, map);
		
		sqlSession.close();
		
		return count;
	}

	@Override
	public List<Board> selectSearchList(PageInfo pi, Map<String, String> map) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		// 원래 컨트롤러에서 만들어야함...
		RowBounds rowBounds = 
				new RowBounds(((pi.getCurrentPage() - 1) * pi.getBoardLimit()), pi.getBoardLimit());
		
		List<Board> list = boardDao.selectSearchList(sqlSession, map, rowBounds);
		
		sqlSession.close();
		
		return list;
	}

	@Override
	public List<Category> selectCategory() {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		List<Category> list = boardDao.selectCategory(sqlSession);
		
		sqlSession.close();
		
		return list;
	}
	
	@Override
	public int insertBoard(Board board, Attachment attachment) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int boardResult = boardDao.insertBoard(sqlSession, board);

		int attachmentResult = 1;
		if(attachment != null) {
			attachmentResult = new BoardDao().insertAttachment(sqlSession, attachment);
		}
		
		if((boardResult * attachmentResult) > 0) {
			sqlSession.commit();
		} else {
			sqlSession.rollback();
		}
		
		sqlSession.close();
		
		return (boardResult * attachmentResult);
	}

	@Override
	public Board findById(int boardNo) {
		
		SqlSession sqlSession = Template.getSqlSession();

		int result = boardDao.increaseCount(sqlSession, boardNo);
		Board board = null;
		
		if(result > 0) {
			System.out.println("나됨?");
			board = boardDao.findById(sqlSession, boardNo);
			sqlSession.commit();
		} else {
			sqlSession.rollback();
		}
		
		sqlSession.close();
		
		return board;
	}

	@Override
	public Attachment selectAttachment(int boardNo) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		Attachment attachment = boardDao.selectAttachment(sqlSession, boardNo);
		
		sqlSession.close();
		
		return attachment;
	}

	@Override
	public int update(Board board, Attachment attachment) {
		
		
		return 0;
	}
	
}
