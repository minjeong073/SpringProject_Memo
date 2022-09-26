package com.ming.project.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ming.project.memo.common.FileManagerService;
import com.ming.project.memo.common.PermissionInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

	@Autowired
	private PermissionInterceptor interceptor;
	// spring bean 에 등록된 class 만 autowired 사용 가능
	
	// 서버의 특정 경로 파일을 
	// 설정한 경로로 외부에서 접근 가능하도록 설정

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH + "/");	// 기본으로 / 가 붙어있지 않기 때문에 한번 더 붙여주기
	}

	// filter 기능 구현
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**")	// intercept 할 페이지 지정해주기
		.excludePathPatterns("/static/**", "/images/**");
	}
	
	
	
}
