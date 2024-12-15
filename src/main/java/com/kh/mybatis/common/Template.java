package com.kh.mybatis.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Template {
	
	// MyBatis
	
	public static SqlSession getSqlSession() {
		
		SqlSession sqlSession = null;
		
		// mybatis-config.xml 파일을 읽어서
		// 해당 DB와 접속된 sqlSession 객체를 생성해서 반환
		
		// src folder 최상위 폴더를 의미(=> 우리는 classes)
		String resource = "/mybatis-config.xml";
		
		try {
			InputStream stream = Resources.getResourceAsStream(resource);
			
			// 1단계 : new SqlSessionFactoryBuilder() : SqlSessionFactoryBuilder 객체 생성
			// 2단계 : build(입력스트림);
			//       스트림으로부터 마이바티스 환경설정 파일을 읽어오면서 SqlSessionFactory 객체 생성
			// 3단계 : openSession(false) : SqlSession객체 생성 및 트랜잭션 처리 시 autoCommit을 안쓰겠다.
			// 		=> 개발자가 직접 트랜잭션을 관리하겠다
			//      .openSession() 인자값을 전달하지 않으면 기본값 false
			sqlSession = new SqlSessionFactoryBuilder().build(stream).openSession(false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sqlSession;
	}

}



















