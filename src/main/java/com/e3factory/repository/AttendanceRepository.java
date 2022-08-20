package com.e3factory.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.e3factory.dto.Attendance;

@Mapper
public interface AttendanceRepository {
	public Attendance findById(int id);
	public List<Attendance> findByCourseId(int courseId);
	public List<Attendance> findDetailByCourseId(int courseId);
	public List<Attendance> findByUserId(String userId);
	public void delete(int id);
	public int getAttendanceCount(int courseId);
	public void insert(@Param("courseId") int courseId, @Param("userId") String userId);
	public void updateEvaluate(Attendance attendance);
}
