package com.ming.project.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ming.project.memo.common.FileManagerService;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

	// 서버의 특정 경로 파일을 
	// 설정한 경로로 외부에서 접근 가능하도록 설정

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH + "/");	// 기본으로 / 가 붙어있지 않기 때문에 한번 더 붙여주기
		
		
	}

	
	
	
	
}
