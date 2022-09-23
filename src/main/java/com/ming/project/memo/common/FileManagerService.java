package com.ming.project.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManagerService {
	
	// final 로 더이상 수정되지 못하도록
	// static 메서드에서 사용할 수 있도록
	public final static String FILE_UPLOAD_PATH = "D:\\minjeong\\spring\\memo\\upload";

	// 파일을 저장하고, 해당 파일을 접근할 수 있는 경로를 리턴해주는 기능
	public static String saveFile(int userId, MultipartFile file) {
		
		// 서버에 저장할 위치 잡아주기
		// 해당 경로 밑에 사용자 별로 dir 생성해서 그 안에 파일 저장
		// -> 사용자 id + 현재 시간을 dir 이름으로
		// 1_16:23:06 일 경우 지저분함  
		// 시간을 UNIX TIME 으로 설정 
		// UNIX TIME : 1970년 1월 1일을 기준으로 현재까지 흐른 시간 (millisecond 1/1000)
		// -> 1_45283974815
		// D:\\minjeong\\spring\\memo\\upload\\1_45283974815\\thisisfile.jpg
		
		// 기본 경로는 \ 으로 하지만, directory 설정 값은 / 로
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis() + "/";
		
		// dir 생성
		
		String filePath = FILE_UPLOAD_PATH + directoryName;
		File directory = new File(filePath);	// file 객체 생성
		// directory.mkdir();	// dir 생성
		
		// file 다루면서 예외 사항 많이 발생함
		// -> 에러 사항 별로 해결 나눠놓기
		
		if (directory.mkdir() == false) {
			return null;	// dir 생성 실패
		}
		
		try {
			// file 꺼내 저장
			byte[] bytes = file.getBytes();
			
			// file 전체 경로
			String fileFullPath = filePath + file.getOriginalFilename();
			
			// file 경로를 관리하는 객체 path
			Path path = Paths.get(fileFullPath);
			
			// file 저장
			Files.write(path, bytes);
			
		} catch (IOException e) {
			e.printStackTrace();
			
			// file 저장 실패인 경우
			return null;
		}
		
		// 클라이언트에서 접근 가능한 경로
		// D:\\minjeong\\spring\\memo\\upload 해당 dir 아래 경로
		// /images/~
		
		return "/images" + directoryName + file.getOriginalFilename();
		
	}
	
	
	// 파일 삭제 기능
	// void 해도 되지만 파일 처리 과정에서 문제가 생길 수 있기 때문에 boolean 으로 리턴 값 받음
	public static boolean removeFile(String filePath) {
		
		// file path : /images/2_98302347296/test.jpg
		
		// 삭제 경로는 /images 를 제거하고 실제 파일 저장 경로를 이어붙이기
		// --> 실제 경로 : D:\\minjeong\\spring\\memo\\upload/images/2_98302347296/test.jpg
		
		
		// + 파일 없는 게시물일 경우 nullPointerException 발생
		if (filePath == null) {
			return true;	// 아래 코드 수행되지 않도록 끝냄
		}
		
		String realFilePath = FILE_UPLOAD_PATH + filePath.replace("/images", "");
		
		Path path = Paths.get(realFilePath);	// 경로 관리하는 클래스
		
		// 바로 삭제 할 때 문제가 생길 수 있기 때문에 우선 파일이 있는지 확인
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		// 파일 저장되어 있던 폴더도 지워야 함
		// 폴더 경로 : D:\\minjeong\\spring\\memo\\upload/images/2_98302347296
		
		// 파일이 포함된 폴더 경로 : getParent() 로 가져오기
		path = path.getParent();
		
		// 폴더 존재하는지 확인
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	
}
