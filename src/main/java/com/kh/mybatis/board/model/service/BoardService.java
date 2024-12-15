package com.kh.mybatis.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.mybatis.board.model.vo.Attachment;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.board.model.vo.Category;
import com.kh.mybatis.board.model.vo.Reply;
import com.kh.mybatis.common.PageInfo;

public interface BoardService {

	// 게시글 관련 기능
	
	// 1. 목록조회(페이징처리)
	int selectListCount(); // 전체 게시글 수
	
	List<Board> selectList(PageInfo pi); // 선택한 페이지에 출력할 게시글 목록
	
	// 2. 상세조회
	int increaseCount(int boardNo); // 게시글 조회수 증가
	
	Board selectBoard(int boardNo); // 게시글 상세 정보 + 댓글정보까지 해버렸음! selectBoardAndReply
	
	List<Reply> selectReplyList(int boardNo); // 전체 댓글 정보

	// 2_2. 댓글 조회 관련
	// 	 1) 동기식 요청으로 게시글의 정보를 조회할 때 select를 한 번 더 수행해서 조회

	//   2) 동시식 요청으로 조회하되 게시글의 정보를 한꺼번에 조회해서 가져갈 예정
	
	
	// 3. 검색서비스(검색된 목록 페이징 처리)
	int searchedCount(Map<String, String> map); // 검색된 목록 수
	List<Board> selectSearchList(PageInfo pi, Map<String, String> map); //
	
	// 카테고리 선택
	List<Category> selectCategory();
	
	// 게시글 작성(추가)
	int insertBoard(Board board, Attachment attachment);
	
	// 작성된 게시글 아이디로 조회 ( 게시글 + 첨부파일 )
	Board findById(int boardNo);
	
	Attachment selectAttachment(int boardNo);
	
	// 게시글 수정
	int update(Board board, Attachment attachment);
	
	 
}
