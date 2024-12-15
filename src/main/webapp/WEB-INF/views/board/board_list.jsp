<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
header.masthead {
	display: none;
}

.row {
	height: 800px;
}

tr:hover {
	cursor: pointer;
}
</style>

<br />
<br />

<jsp:include page="../include/header.jsp" />

<!-- Begin Page Content -->
<div class="container">
	<div class="row">
		<div class="col-lg-1"></div>
		<div class="col-lg-10">
			<div class="panel-body">
				<h2 class="page-header">
					<span style="color: #52b1ff;">KH</span> 자유 게시판
					<c:if test="${ not empty loginUser }">
						<a href="enrollForm.board" class="btn float-right"
							style="background-color: #52b1ff; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">글쓰기</a>
					</c:if>
				</h2>
				<table class="table table-bordered table-hover">
					<thead>
						<tr
							style="background-color: #52b1ff; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">
							<th width="100">번호</th>
							<th width="150">카테고리</th>
							<th width="150">작성자</th>
							<th width="450">제목</th>
							<th width="200">작성일</th>
							<th width="100">조회수</th>
						</tr>
					</thead>
					<tbody>
						<!-- if list가 비어있다면 조회결과가 없다고 표시 -->
						<c:forEach items="${ list }" var="board">
							<tr style="color: #52b1ff;" class="board" id="${ board.boardNo }">
								<td>${ board.boardNo }</td>
								<td>${ board.category }</td>
								<td>${ board.boardWriter }</td>
								<td style="color: #52d6ffcc;">${ board.boardTitle } &nbsp;
								</td>
								<td>${ board.createDate }</td>
								<td>${ board.count }</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>

				<script>
			   		$(function(){
			   			
			   			$('.board').click(e => {
			   				
			   				// location.href = 'detail.board/board?boardNo=' + targetId;
			   				// boardNo 필요함~
			   				// detail.board
			   				// console.log(e.currentTarget.id);
			   				
			   				const targetId = e.currentTarget.id;
			   				
			   				location.href = 'detail.board?boardNo=' + targetId;
			   			});
			   		});
               
            	</script>
			</div>

			<div id="search-area" class="form-group">
				<form action="search.board" method="get">
					<input type="hidden" name="currentPage" value="1" />
					<select class="form-control" name="condition">
						<option value="writer">작성자</option>
						<option value="content">내용</option>
						<option value="title">제목</option>
					</select> 
					<input type="text" name="keyword" class="form-control" />
					<button type="submit" class="btn btn-outline-primary btn-block">검색</button>
				</form>
			</div>
			
			<script>
				window.onload = function(){
					
					$('option[value="${condition}"]').prop('selected', true);
					
					const currentUrl = window.location.href;
					// console.log(currentUrl);

					const urlObj = new URL(currentUrl);
					
					const keyword = urlObj.searchParams.get('keyword');
					
					const keyword2 = (new URL(window.location.href)).searchParams.get('keyword');
					
					console.log(`keyword2 : \${keyword2}`);
				}
			</script>
			
			<div class="paging-area" align="center">

				<!-- 
				<c:if test="${ pi.currentPage > 1 }">
					<button class="btn btn-outline-primary" style="color: #52b1ff;"
						onclick="location.href='list.board?currentPage=${ pi.currentPage - 1}'">이전</button>
				</c:if>
 				-->


				<c:forEach begin="${ pi.startPage }" end="${ pi.endPage }" var="i">
					<c:choose>
						<c:when test="${ empty condition }">
							<button class="btn btn-outline-primary" style="color: #52b1ff;"
								onclick="location.href='list.board?currentPage=${ i }'">${ i }</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-outline-primary" style="color: #52b1ff;"
								onclick="location.href='search.board?currentPage=${i}&condition=${condition}&keyword=${keyword}'">${ i }</button>
						</c:otherwise>		
					</c:choose>
				</c:forEach>


				<!-- 
				<c:if test="${ pi.currentPage ne pi.maxPage }">
					<button class="btn btn-outline-primary" style="color: #52b1ff;"
						onclick="location.href='list.board?currentPage=${ pi.getEndPage() + 1 }'">다음</button>
				</c:if>
				-->
			</div>
		</div>
	</div>
</div>



<jsp:include page="../include/footer.jsp" />


