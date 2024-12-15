package com.kh.mybatis.common;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyRenamePolicy implements FileRenamePolicy {
	
	// FileRenamePolicy 인터페이스가 가지고 있는 rename 추상메소드가 존재!
	// rename 메소드를 오버라이딩해서 기존파일명을 전달받아서 파일명을 수정한 뒤
	// 수정한 파일을 반환해줄 것
	
	@Override
	public File rename(File originFile) {
		
		// 확장자명은 뽑아서 써야하기 때문에 "원본 파일명"을 알아내야함
		String originName = originFile.getName();
		
		// 우리만의 이름 규칙 => 최대한 이름이 안겹치도록
		// KH_Bclass_super_ 년월일시분초 랜덤값 + 원본파일 확장자
		
		/*
		 * 원본명						바꾸기
		 * 
		 * bono.jpg 			=> KH_Bclass_super_202412031402339999.jpg
		 */
		
		// 1. 원본파일의 확장자명
		String ext = originName.substring(originName.lastIndexOf("."));
		
		// 2. 랜덤값 
		int randomNum = (int)(Math.random() * 9000) + 1000;
		
		// 3. 년월일시분초
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		// 1 + 2 + 3 조합해서 수정 파일명을 변수에 대입
		String changeName = "KH_Bclass_super_" + currentTime + "_" + randomNum + ext; 
		
		return new File(originFile.getParent(), changeName);
	}
	
	
	
}
