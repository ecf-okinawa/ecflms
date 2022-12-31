package com.e3factory.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e3factory.common.dto.Course;
import com.e3factory.common.repository.CourseRepository;
import com.e3factory.common.security.AuthEntity;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public List<Course> courseView(AuthEntity principal){
		//講座情報取得
		List<String> auths = AuthEntity.getRoleNames(principal);
		List<Course> list = null;
		if( auths.contains("ROLE_ADMIN") ) {	//管理者
			//全講座を閲覧
			list = courseRepository.findAll();
		} else if( auths.contains("ROLE_TEACHER") ) {	//講師
			//担当講座を閲覧
			list = courseRepository.findByUserId(principal.getUsername());
		}
		return list;
	}

	@Transactional
	public void doCourseInsert(Course course) {
		//DBへ登録
		courseRepository.insert(course);
	}

	public Course courseUpdate(String id) {
		return courseRepository.findById(Integer.parseInt(id));
	}

	@Transactional
	public void doCourseUpdate(Course course) {
		//DB更新
		courseRepository.update(course);
	}

	public Course courseDelete(String id) {
		return courseRepository.findById(Integer.parseInt(id));
	}

	@Transactional
	public void doCourseDelete(int id) {
		courseRepository.delete(id);
	}
}
