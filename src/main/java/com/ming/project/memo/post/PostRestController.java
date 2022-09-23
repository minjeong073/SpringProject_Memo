package com.ming.project.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ming.project.memo.post.bo.PostBO;

@RestController
@RequestMapping("/post")
public class PostRestController {

	@Autowired
	private PostBO postBO;
	
	// 메모 입력 기능
	@PostMapping("/create")
	public Map<String, String> create(
			@RequestParam("title") String title
			, @RequestParam("content") String content
			, @RequestParam(value="file", required = false) MultipartFile file	// false -> 필수 X
			, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		int userId = (Integer) session.getAttribute("userId");
		
		int count = postBO.addPost(userId, title, content, file);
		
		Map<String, String> result = new HashMap<>();
		
		if (count == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
	// 메모 수정 기능
	@PostMapping("/update")
	public Map<String, String> updatePost(
			@RequestParam("postId") int postId
			, @RequestParam("title") String title
			, @RequestParam("content") String contents) {
		
		int count = postBO.updatePost(postId, title, contents);
		
		Map<String, String> result = new HashMap<>();
		
		if (count == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
	// 메모 삭제 기능
	@GetMapping("/delete")
	public Map<String, String> deletePost(@RequestParam("postId") int postId) {
		
		int count = postBO.deletePost(postId);
		
		Map<String, String> result = new HashMap<>();
		
		if (count == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
}
