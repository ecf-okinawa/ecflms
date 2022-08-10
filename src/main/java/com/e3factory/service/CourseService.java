package com.e3factory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e3factory.dto.Course;
import com.e3factory.form.CourseForm;
import com.e3factory.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public List<Course> findAll(){
		//DBからユーザー情報取得
		List<Course> list = courseRepository.findAll();
		return list;
	}

	public List<Course> findByUserId(String userId){
		//DBからユーザー情報取得
		List<Course> list = courseRepository.findByUserId(userId);
		return list;
	}

	public Course findById(String id) {
		return courseRepository.findById(Integer.parseInt(id));
	}

	@Transactional
	public void insert(CourseForm form) {
		//FormからDtoへコピー
		Course course = new Course();
		course.setId(form.getId());
		course.setName(form.getName());
		course.setUserId(form.getUserId());
		course.setStartDate(form.getStartDate());
		course.setEndDate(form.getEndDate());
		course.setCapacity(form.getCapacity());
		course.setLocation(form.getLocation());

		//DBへ登録
		courseRepository.insert(course);
	}

	@Transactional
	public void update(CourseForm form) {
		//FormからDtoへコピー
		Course course = new Course();
		course.setId(form.getId());
		course.setName(form.getName());
		course.setUserId(form.getUserId());
		course.setStartDate(form.getStartDate());
		course.setEndDate(form.getEndDate());
		course.setCapacity(form.getCapacity());
		course.setLocation(form.getLocation());

		//DB更新
		courseRepository.update(course);
	}

	@Transactional
	public void delete(int id) {
		courseRepository.delete(id);
	}
}
