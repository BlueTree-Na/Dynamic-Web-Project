package com.kh.mybatis.board.model.vo;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Board {

	private int boardNo;
	private int boardType;
	private String category; // 테이블에는 숫자타입이지만 카테고리 이름을 가져올 것이기때문에 문자열로 선언
	private String boardTitle;
	private String boardContent;
	private String boardWriter; // 테이블에는 숫자타입으로 되어있지만 작성자 이름을 가져올것이기 때문에 문자열로 선언
	private int count;
	private Date createDate;
	private String status;
	
	
	private List<Reply> replyList;
	
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	
	
	private String imagePath;
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public Board() {
		super();
	}
	public Board(int boardNo, int boardType, String category, String boardTitle, String boardContent,
			String boardWriter, int count, Date createDate, String status) {
		super();
		this.boardNo = boardNo;
		this.boardType = boardType;
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.count = count;
		this.createDate = createDate;
		this.status = status;
	}
	
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getBoardType() {
		return boardType;
	}
	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boardType=" + boardType + ", category=" + category + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardWriter=" + boardWriter + ", count=" + count
				+ ", createDate=" + createDate + ", status=" + status + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(boardContent, boardNo, boardTitle, boardType, boardWriter, category, count, createDate,
				status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		return Objects.equals(boardContent, other.boardContent) && boardNo == other.boardNo
				&& Objects.equals(boardTitle, other.boardTitle) && boardType == other.boardType
				&& Objects.equals(boardWriter, other.boardWriter) && Objects.equals(category, other.category)
				&& count == other.count && Objects.equals(createDate, other.createDate)
				&& Objects.equals(status, other.status);
	}
}
