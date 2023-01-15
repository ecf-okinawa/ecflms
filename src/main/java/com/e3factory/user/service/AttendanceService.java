package com.e3factory.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.e3factory.common.dto.Course;
import com.e3factory.common.repository.AttendanceRepository;
import com.e3factory.common.repository.CourseRepository;

@Service
public class AttendanceService {
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 受講登録画面の情報を取得
	 * @param model
	 * @param courseId
	 */
	public void attendanceInsert(Model model, int courseId) {
		Course course = courseRepository.findById(courseId);
		model.addAttribute("course", course);
	}

	/**
	 * 受講情報を登録する
	 * @param model
	 * @param courseId
	 * @param userId
	 */
	public void doAttendanceInsert(Model model, int courseId, String userId) {
		attendanceRepository.insert(courseId, userId);
	}
}
