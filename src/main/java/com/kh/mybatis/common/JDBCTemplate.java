package com.kh.mybatis.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class JDBCTemplate {
	
	public static void registerDriver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		
		Connection conn = null;
		Properties prop = new Properties();
		String file = JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath();
		
		try{
			prop.load(new FileInputStream(file));
//			String strA = prop.getProperty("A");
//			System.out.println("A키값의 Value : " + strA);
			
			// 결국 또 유지보수~! 편해짐~!
			// 환경설정정보 : Java Collection Properties
			conn = DriverManager.getConnection(prop.getProperty("url"),
											   prop.getProperty("username"), 
											   prop.getProperty("password"));
			conn.setAutoCommit(false);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// Connection 객체를 이용해서 commit 시켜주는 메소드
	public static void commit(Connection conn) {
		try{
			if(conn != null && !conn.isClosed()) conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Connection 객체를 이용해서 rollback 시켜주는 메소드
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.rollback();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// JDBC용 객체를 반납시켜주는 메소드(각 객체별로)--------------------------
	
	// Connection 객체를 전달받아서 반납시켜주는 메소드
	public static void close(Connection conn) {
		try{
			if(conn != null && !conn.isClosed()) conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Statement 객체를 전달받아서 반납시켜주는 메소드
	public static void close(Statement stmt) {
		try{
			if(stmt != null && !stmt.isClosed()) stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ResultSet 객체를 전달받아서 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) rset.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
