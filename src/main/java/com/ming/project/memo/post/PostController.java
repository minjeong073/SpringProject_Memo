package com.ming.project.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ming.project.memo.post.bo.PostBO;
import com.ming.project.memo.post.model.Post;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostBO postBO;
	
	@GetMapping("/list/view")
	public String list(HttpServletRequest req
			, Model model) {

		// 데이터 조회 과정
		
		HttpSession session = req.getSession();
		int userId = (Integer) session.getAttribute("userId");

		List<Post> postList = postBO.getPostList(userId);
		
		model.addAttribute("postList", postList);
		
		return "post/list";
	}
	
	@GetMapping("/create/view")
	public String memoInput() {
		return "post/input";
	}
	
	@GetMapping("/detail/view")
	public String memoDetail(@RequestParam("id") int id
			, Model model) {
		
		Post post = postBO.getPost(id);
		
		model.addAttribute("post", post);
		
		return "post/detail";
	}
	
	@GetMapping("/edit/view")
	public String memoEdit(@RequestParam("id") int id
			, Model model) {
		
		Post post = postBO.getPost(id);
		
		model.addAttribute("post", post);
		
		return "post/edit";
	}
}
