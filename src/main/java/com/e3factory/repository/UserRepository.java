package com.e3factory.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.e3factory.dto.User;

@Mapper
public interface UserRepository {
	public User findById(String id);
	public List<User> findAll();
	public void insert(User user);
	public void update(User user);
	public void delete(String id);
}
