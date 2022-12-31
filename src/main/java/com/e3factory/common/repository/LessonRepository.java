package com.e3factory.common.repository;

import org.apache.ibatis.annotations.Mapper;

import com.e3factory.common.dto.Lesson;

@Mapper
public interface LessonRepository {
	public int getLessonCount();
	public Lesson getLesson(Integer id);
	public void insert(Lesson lesson);
	public void update(Lesson lesson);
}
