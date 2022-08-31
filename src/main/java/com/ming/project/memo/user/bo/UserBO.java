package com.ming.project.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ming.project.memo.common.EncryptUtils;
import com.ming.project.memo.user.dao.UserDAO;
import com.ming.project.memo.user.model.User;

@Service
public class UserBO {

	@Autowired
	private UserDAO userDAO;
	
	// 회원 정보를 user 테이블에 저장
	public int addUser(String loginId, String password, String name, String email) {
		
		// 비밀번호 암호화
		String encryptPassword = EncryptUtils.md5(password);
		
		return userDAO.insertUser(loginId, encryptPassword, name, email);
	}
	
	// 아이디 패스워드로 사용자 조회
	// 조회된 정보를 기반으로 객체 형태로 리턴 (로그인 한 상태이면 계속 쓸 정보이기 때문)
	public User getUser(String loginId, String password) {
		
		// 조회할 비밀번호 암호화 후 조회
		String encryptPassword = EncryptUtils.md5(password);
		
		return userDAO.selectUser(loginId, encryptPassword);
	}
}

