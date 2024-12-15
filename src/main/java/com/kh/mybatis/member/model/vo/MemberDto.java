package com.kh.mybatis.member.model.vo;

import java.util.Objects;

public class MemberDto {
	
	private int userNo;
	private String userPwd;
	private String changePwd;
	public MemberDto() {
		super();
	}
	public MemberDto(int userNo, String userPwd, String changePwd) {
		super();
		this.userNo = userNo;
		this.userPwd = userPwd;
		this.changePwd = changePwd;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getChangePwd() {
		return changePwd;
	}
	public void setChangePwd(String changePwd) {
		this.changePwd = changePwd;
	}
	@Override
	public String toString() {
		return "MemberDto [userNo=" + userNo + ", userPwd=" + userPwd + ", changePwd=" + changePwd + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(changePwd, userNo, userPwd);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberDto other = (MemberDto) obj;
		return Objects.equals(changePwd, other.changePwd) && userNo == other.userNo
				&& Objects.equals(userPwd, other.userPwd);
	}
	
}
