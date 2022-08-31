package com.ming.project.memo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
	// static method
	// 멤버변수 관련 없이 순수하게 메서드만 사용할 때 (객체 생성 필요 X)
	
	// 암호화 기능
	public static String md5(String message) {
		
		
		// try-catch 안에 있으면 return 이 안되기 때문에 바깥쪽에 선언
		String resultData = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			
			// 문자열을 byte[] 로 변경
			byte[] bytes = message.getBytes();
			
			// 암호화
			md.update(bytes);
			
			// 암호화된 결과 얻어오기
			byte[] digest = md.digest();
			
			// 16진수 형태의 문자열로 변환
			// (배열 값 하나씩 꺼내서 16진수로 변환)
			for (int i = 0; i < digest.length; i++) {
				resultData += Integer.toHexString(digest[i] & 0xff); // 비트연산
			}
			
			
			// 문자열 암호화 방식이 알 수 없는 것일 때 예외 처리
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultData;
		
	}
	

}
