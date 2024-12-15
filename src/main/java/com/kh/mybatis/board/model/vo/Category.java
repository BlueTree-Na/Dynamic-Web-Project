package com.kh.mybatis.board.model.vo;

import java.util.Objects;

public class Category {
	
	private int categoryNo;
	private String categoryName;
	
	public Category() {
		super();
	}
	public Category(int categoryNo, String categoryName) {
		super();
		this.categoryNo = categoryNo;
		this.categoryName = categoryName;
	}
	
	public int getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public String toString() {
		return "Category [categoryNo=" + categoryNo + ", categoryName=" + categoryName + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(categoryName, categoryNo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(categoryName, other.categoryName) && categoryNo == other.categoryNo;
	}
	
}
