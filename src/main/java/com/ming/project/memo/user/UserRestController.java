package com.ming.project.memo.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ming.project.memo.user.bo.UserBO;
import com.ming.project.memo.user.model.User;

@RestController	// @Controller + @ResponseBody
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserBO userBO;
	
	// 회원 정보를 입력 받고 회원가입을 진행하는 기능
	@PostMapping("/signup")
	public Map<String, String> signup(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("email") String email) {
		
		int count = userBO.addUser(loginId, password, name, email);
		
		Map<String, String> result = new HashMap<>();
		
		if (count == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		return result;
	}
	
	// 로그인 과정을 진행하는 기능
	@PostMapping("/signin")
	public Map<String, String> signin(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, HttpServletRequest req) {
		
		
		User user = userBO.getUser(loginId, password);
		
		Map<String, String> result = new HashMap<>();
		
		if (user != null) {
			result.put("result", "success");

			HttpSession session = req.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
}
