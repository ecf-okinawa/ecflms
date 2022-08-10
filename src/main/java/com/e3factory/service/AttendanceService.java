package com.e3factory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e3factory.dto.Attendance;
import com.e3factory.dto.Course;
import com.e3factory.repository.AttendanceRepository;
import com.e3factory.repository.CourseRepository;
import com.e3factory.util.AppMessage;

@Service
public class AttendanceService {
	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private MessageSource messageSource;

	public List<Attendance> findByCourseId(String courseId){
		return attendanceRepository.findByCourseId(Integer.parseInt(courseId));
	}

	public Attendance findById(String id) {
		return attendanceRepository.findById(Integer.parseInt(id));
	}

	@Transactional
	public List<AppMessage> attend(int courseId, String userId) {
		List<AppMessage> messages = new ArrayList<AppMessage>();
		//講座定員数を取得
		Course course = courseRepository.findById(courseId);
		//講座申込数を取得
		int attendanceCount = attendanceRepository.getAttendanceCount(courseId);
		//申込数超過の場合はエラー
		if( course.getCapacity() <= attendanceCount ) {
			messages.add( AppMessage.getMessage(messageSource, "w.attendance.001", AppMessage.Level.WARN) );
			return messages;
		}
		//受講登録を実行
		attendanceRepository.insert(courseId, userId);
		//成功メッセージ
		if( messages.size() == 0 ){
			messages.add( AppMessage.getMessage(messageSource, "i.attendance.001", AppMessage.Level.INFO) );
		}

		return messages;
	}

	@Transactional
	public void delete(int id) {
		attendanceRepository.delete(id);
	}
}
