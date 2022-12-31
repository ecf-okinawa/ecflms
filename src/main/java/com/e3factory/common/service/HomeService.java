package com.e3factory.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3factory.common.dto.Attendance;
import com.e3factory.common.dto.Course;
import com.e3factory.common.form.HomeInfo;
import com.e3factory.common.repository.AttendanceRepository;
import com.e3factory.common.repository.CourseRepository;

@Service
public class HomeService {
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;

	/**
	 * ユーザーホーム画面用フォーム情報を返す
	 * @param userId
	 * @return
	 */
	public List<HomeInfo> getHomeInfo(String userId, boolean attended){
		//戻り値用リスト
		ArrayList<HomeInfo> list = new ArrayList<HomeInfo>();
		//講座情報の取得
		List<Course> courseList = courseRepository.findAll();
		//受講情報の取得
		List<Attendance> attendanceList = attendanceRepository.findByUserId(userId);
		//受講済講座のIDを取得
		List<Integer> attendedIds =
				attendanceList.stream().map(dto -> dto.getCourseId()).collect(Collectors.toList());
		//表示用リストにセット
		for( Course course : courseList ) {
			HomeInfo info = new HomeInfo();
			info.setCourseId(course.getId());
			info.setCourseName(course.getName());
			info.setTeacherName(course.getTeacherName());
			info.setImgPath(course.getImgPath());
			//指定ユーザーの受講状況を登録
			if( attendedIds.contains(course.getId()) ) {
				info.setAttendance(true);
			} else {
				info.setAttendance(false);
			}
			//登録済表示モードかつ未登録以外はリストへ追加
			if( !(attended && info.isAttendance() == false) ) {
				list.add(info);
			}
		}

		return list;
	}

	/**
	 * 受講登録用フォームを返す
	 * @param courseId
	 * @return
	 */
	public HomeInfo getHomeInfo(int courseId) {
		Course course = courseRepository.findById(courseId);
		//フォームへ移行
		HomeInfo info = new HomeInfo();
		info.setCourseId(course.getId());
		info.setCourseName(course.getName());
		info.setTeacherName(course.getTeacherName());
		return info;
	}
}
