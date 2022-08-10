package com.e3factory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3factory.dto.Attendance;
import com.e3factory.dto.CourseInfo;
import com.e3factory.form.HomeInfo;
import com.e3factory.repository.AttendanceRepository;
import com.e3factory.repository.CourseInfoRepository;

@Service
public class HomeService {
	@Autowired
	private CourseInfoRepository courseInfoRepository;
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
		List<CourseInfo> courseList = courseInfoRepository.find();
		//受講情報の取得
		List<Attendance> attendanceList = attendanceRepository.findByUserId(userId);
		//受講済講座のIDを取得
		List<Integer> attendedIds =
				attendanceList.stream().map(dto -> dto.getCourseId()).collect(Collectors.toList());
		//表示用リストにセット
		for( CourseInfo course : courseList ) {
			HomeInfo info = new HomeInfo();
			info.setId(course.getId());
			info.setName(course.getName());
			info.setTeacherName(course.getTeacherName());
			info.setStartDate(course.getStartDate());
			info.setEndDate(course.getEndDate());
			info.setCapacity(course.getCapacity());
			info.setLocation(course.getLocation());
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
		CourseInfo course = courseInfoRepository.findById(courseId);
		//フォームへ移行
		HomeInfo info = new HomeInfo();
		info.setId(course.getId());
		info.setName(course.getName());
		info.setTeacherName(course.getTeacherName());
		info.setStartDate(course.getStartDate());
		info.setEndDate(course.getEndDate());
		info.setCapacity(course.getCapacity());
		info.setLocation(course.getLocation());
		return info;
	}
}
