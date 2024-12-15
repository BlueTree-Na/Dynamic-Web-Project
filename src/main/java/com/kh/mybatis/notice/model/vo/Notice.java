package com.kh.mybatis.notice.model.vo;

import java.sql.Date;
import java.util.Objects;

public class Notice {
	
	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private int noticeWriter; // 회원 번호로 찾을거임~
	private int count;
	private Date createDate;
	private String status;
	
	public Notice() {
		super();
	}
	public Notice(int noticeNo, String noticeTitle, String noticeContent, int noticeWriter, int count, Date createDate,
			String status) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeWriter = noticeWriter;
		this.count = count;
		this.createDate = createDate;
		this.status = status;
	}
	
	public int getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public int getNoticeWriter() {
		return noticeWriter;
	}
	public void setNoticeWriter(int noticeWriter) {
		this.noticeWriter = noticeWriter;
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
		return "Notice [noticeNo=" + noticeNo + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ ", noticeWriter=" + noticeWriter + ", count=" + count + ", createDate=" + createDate + ", status="
				+ status + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(count, createDate, noticeContent, noticeNo, noticeTitle, noticeWriter, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notice other = (Notice) obj;
		return count == other.count && Objects.equals(createDate, other.createDate)
				&& Objects.equals(noticeContent, other.noticeContent) && noticeNo == other.noticeNo
				&& Objects.equals(noticeTitle, other.noticeTitle) && noticeWriter == other.noticeWriter
				&& Objects.equals(status, other.status);
	}
}
