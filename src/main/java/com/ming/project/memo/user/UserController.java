package com.ming.project.memo.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	// 회원가입
	@GetMapping("/signup/view")
	public String signupView() {
		return "user/signup";
	}
	
	// 로그인
	@GetMapping("/signin/view")
	public String signinView() {
		return "user/signin";
	}
	
	// 로그아웃
	@GetMapping("/signout")
	public String signout(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		
		return "redirect:/user/signin/view";
	}
}
