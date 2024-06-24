package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;

	public boolean insert(BlogVo blogVo) {
		int count = sqlSession.insert("blog.insert", blogVo);
		
		return count == 1;
	}
	
	public boolean insertCategory(CategoryVo cVo) {
		int count = sqlSession.insert("category.insert", cVo);
		
		return count == 1;
	}

	public boolean insertPost(PostVo pVo) {
		int count = sqlSession.insert("blog.insertPost", pVo);
		
		return count == 1;
	}

	public BlogVo getBlogById(String blogId) {
		
		return sqlSession.selectOne("blog.getBlogById", blogId);
	}
	
	public List<Map<String, Object>> getCategoryById(String blogId) {
		
		return sqlSession.selectList("category.findAll", blogId);
	}
	
	public List<PostVo> getPost(Map<String, Object> map) {
		
		return sqlSession.selectList("blog.getPost", map);
	}

	public boolean update(BlogVo blogVo) {
		int count = sqlSession.update("blog.update", blogVo);
		
		return count == 1;
	}
	
	public int changeCategoryPosts(Long no, String blogId) {
		Map<String,Object> map = new HashMap<>();
		map.put("blogId", blogId);
		map.put("no", no);
		int count = sqlSession.update("category.changeCategoryPosts", map);
		
		return count;
	}

	public boolean deleteCategory(Long no) {
		int count = sqlSession.delete("category.delete", no);
		
		return count==1;
	}

	public int categoryPostCount(Long no) {
		int count = sqlSession.selectOne("category.findPostCount", no);
		
		return count;
	}
}