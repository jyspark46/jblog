package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.UserVo;

@Repository
public class UserRepository {
	private SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		
		return count == 1;
	}

	public UserVo findByEmailAndPassword(String id, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("password", password);
		
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}

	public boolean findById(String blogId) {
		int count = sqlSession.selectOne("user.findById", blogId);
		
		return count==1;
	}

	public UserVo findByEmail(String email) {
		
		return sqlSession.selectOne("user.findByEmail", email);
	}
}