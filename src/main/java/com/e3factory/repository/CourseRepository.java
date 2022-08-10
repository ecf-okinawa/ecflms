package com.e3factory.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.e3factory.dto.Course;

@Mapper
public interface CourseRepository {
	public Course findById(int id);
	public List<Course> findByUserId(String userId);
	public List<Course> findAll();
	public void insert(Course course);
	public void update(Course course);
	public void delete(int id);
}
