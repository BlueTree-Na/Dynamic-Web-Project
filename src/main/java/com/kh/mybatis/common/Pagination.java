package com.kh.mybatis.common;

public class Pagination {

	public Pagination() {
		super();
	}

	public static PageInfo getPageInfo(int listCount, int currentPage, int boardLimit, int pageLimit) {
		
		int maxPage = (int)(Math.ceil((double)listCount / boardLimit));
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if(endPage > maxPage) endPage = maxPage; // endPage가 maxPage보다 클 때, 보여줄 페이지가 없음
		
		return new PageInfo(listCount, currentPage, boardLimit, pageLimit, maxPage, startPage, endPage);
	}
	
}
