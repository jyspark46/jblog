package com.poscodx.jblog.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;

@Service
public class UserService {
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
		blogVo.setLogo("/assets/upload-images/init-logo.png");
		blogRepository.insert(blogVo);
		
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setNo(1L);
		categoryVo.setName("미분류");
		categoryVo.setDescription("카테고리를 지정하지 않은 경우");
		categoryVo.setBlogId(userVo.getId());
		blogRepository.insertCategory(categoryVo);
	}
	
	public UserVo getUser(String id, String password) {
		
		return userRepository.findByEmailAndPassword(id, password);
	}
	
	public UserVo getUser(String email) {
		
		return userRepository.findByEmail(email);
	}

	public boolean findById(String blogId) {
		
		return userRepository.findById(blogId);
	}
}