package com.e3factory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e3factory.dto.Attendance;
import com.e3factory.dto.Course;
import com.e3factory.dto.User;
import com.e3factory.form.AttendanceForm;
import com.e3factory.repository.AttendanceRepository;
import com.e3factory.repository.CourseRepository;
import com.e3factory.repository.UserRepository;
import com.e3factory.util.AppMessage;

@Service
public class AttendanceService {
	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository UserRepository;
	@Autowired
	private MessageSource messageSource;

	public List<AttendanceForm> findByCourseId(String courseId){
		List<Attendance> attendList = attendanceRepository.findDetailByCourseId(Integer.parseInt(courseId));
		//フォームへ移動
		ArrayList<AttendanceForm> formList = new ArrayList<AttendanceForm>();
		for( Attendance attendance : attendList ) {
			AttendanceForm form = new AttendanceForm();
			form.setId(attendance.getId());
			form.setCourseId(attendance.getCourseId());
			form.setCourseName(attendance.getCourseName());
			form.setUserId(attendance.getUserId());
			form.setUserName(attendance.getUserName());
			form.setScore(attendance.getScore());
			form.setRank(attendance.getRank());
			formList.add(form);
		}
		return formList;
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

	/**
	 * 評価画面表示用フォームを返す
	 * @param id
	 * @return
	 */
	public AttendanceForm viewEvaluate(int id) {
		Attendance attendance = attendanceRepository.findById(id);
		User student = UserRepository.findById(attendance.getUserId());

		//フォームへコピー
		AttendanceForm form = new AttendanceForm();
		form.setId(id);
		form.setCourseId(attendance.getCourseId());
		form.setCourseName(attendance.getCourseName());
		form.setUserId(attendance.getUserId());
		form.setUserName(student.getName());
		form.setScore(attendance.getScore());
		form.setRank(attendance.getRank());

		return form;
	}

	public void updateEvaluate(AttendanceForm form) {
		//formからdtoへコピー
		Attendance attendance = new Attendance();
		attendance.setId(form.getId());
		attendance.setScore(form.getScore());
		attendance.setRank(form.getRank());
		//更新処理
		attendanceRepository.updateEvaluate(attendance);
	}
}
