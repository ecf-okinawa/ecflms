package com.e3factory.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.e3factory.dto.CourseInfo;

@Mapper
public interface CourseInfoRepository {
	public List<CourseInfo> find();
	public CourseInfo findById(@Param("id") int id);
}
