package com.ming.project.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ming.project.memo.common.FileManagerService;
import com.ming.project.memo.post.dao.PostDAO;
import com.ming.project.memo.post.model.Post;

@Service
public class PostBO {

	@Autowired
	private PostDAO postDAO;
	
	public int addPost(int userId, String title, String content, MultipartFile file) {
		
		// file 을 서버 특정 위치에 저장
		// 해당 파일을 접근할 수 있는 주소 경로를 dao 로 전달
		
		String imagePath = null;
		
		// file 이 있으면 경로 설정
		if (file != null) {
			imagePath = FileManagerService.saveFile(userId, file);
			
			// imagePath 가 null 일 경우에는 insertPost 를 하지 않고 중단해야함
			if (imagePath == null) {
				// 파일 저장 실패
				return -1;
			}
			
		}
		
		return postDAO.insertPost(userId, title, content, imagePath);
	}
	
	// 로그인 한 사용자의 메모 리스트 얻어오는 기능
	public List<Post> getPostList(int userId) {
		return postDAO.selectPostList(userId);
	}
	
	// id 와 일치하는 하나의 메모 얻어오는 기능
	public Post getPost(int id) {
		return postDAO.selectPost(id);
	}
	
	// 메모 수정
	public int updatePost(int postId, String title, String contents) {
		return postDAO.updatePost(postId, title, contents);
	}
	
	// 메모 삭제
	public int deletePost(int postId) {
		
		// 이미지 경로가 저장된 post 정보 조회
		Post post = postDAO.selectPost(postId);
		
		FileManagerService.removeFile(post.getImagePath());
		// 파일이 삭제가 안될 경우 사용자에게 보여지거나 에러 발생하게 하지 않고
		// 로그로 처리해야함 (서비스이기 때문에)
		
		return postDAO.deletePost(postId);
	}
	
}
