package com.poscodx.jblog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Service
public class BlogService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Transactional(readOnly=false)
	public void join(@Valid UserVo userVo) {
		userRepository.insert(userVo);
		
		BlogVo blogVo = new BlogVo();
		blogVo.setUserId(userVo.getId());
		blogVo.setTitle(userVo.getId() + "의 블로그");
		blogVo.setLogo("/assets/upload-images/init-logo");
		blogRepository.insert(blogVo);
	}
	
	public boolean insertCategory(CategoryVo cVo) {
		
		return blogRepository.insertCategory(cVo);
	}

	public boolean insertPost(PostVo pVo) {
		
		return blogRepository.insertPost(pVo);
	}

	public BlogVo getBlogById(String blogId) {
		BlogVo blogVo = blogRepository.getBlogById(blogId);
		
		return blogVo;
	}
	
	public List<Map<String, Object>> getCategoryById(String blogId) {
		List<Map<String, Object>> cList = new ArrayList<Map<String, Object>>();
		cList = blogRepository.getCategoryById(blogId);

		return cList;
	}
	
	public List<PostVo> getPost(Map<String, Object> map) {
		
		return blogRepository.getPost(map);
	}

	public void update(BlogVo blogVo) {
		blogRepository.update(blogVo);
	}
	
	public int changeCategoryPosts(Long no, String blogId) {
		
		return blogRepository.changeCategoryPosts(no, blogId);
	}

	public void deleteCategory(Long no) {
		blogRepository.deleteCategory(no);
	}

	public int categoryPostCount(Long no) {
		
		return blogRepository.categoryPostCount(no);
	}
}