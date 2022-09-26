package com.ming.project.memo.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
			HttpServletRequest req
			, HttpServletResponse resp
			, Object handler) throws IOException {
		
		HttpSession session = req.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		
		String uri = req.getRequestURI();
		
		// 로그인이 되어있는 경우
		// 로그인 페이지, 회원가입 페이지 접속하면
		// 메모 리스트 페이지로 이동
		if (userId != null) {
			
			// 회원가입 : /user/signup/view
			// 로그인 : /user/signin/view 
			
			if (uri.startsWith("/user")) {
				resp.sendRedirect("/post/list/view");
				return false;
			}
		} else {
			
			// 로그인 되어있지 않은 경우
			// 리스트 페이지, 입력화면, 디테일 화면 접속 시도하면
			// 로그인 페이지로 이동
			
			if (uri.startsWith("/post")) {
				resp.sendRedirect("/user/signin/view");
				return false;
			}
		}
		
		return true;
		
	}
	
	@Override
	public void postHandle(
			HttpServletRequest req
			, HttpServletResponse resp
			, Object handler
			, ModelAndView modelAndView) {
		
	}
	
	
	@Override
	public void afterCompletion(
			HttpServletRequest req
			, HttpServletResponse resp
			, Object handler
			, Exception ex) {
		
	}
	
	
}
