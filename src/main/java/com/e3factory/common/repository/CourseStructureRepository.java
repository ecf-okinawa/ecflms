package com.e3factory.common.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.e3factory.common.dto.CourseContent;
import com.e3factory.common.form.CourseStructureForm;

@Mapper
public interface CourseStructureRepository {
	/**
	 * 講座IDをキーとして、講座構成を取得
	 * @param courseId
	 * @return
	 */
	public CourseContent findCourseStructureByCourseId(int courseId);

	/**
	 * 講座IDをキーとして講座構成を削除
	 * @param id
	 */
	public void deleteCourseById(Integer id);

	/**
	 * 講座構成一括登録
	 * @param contentsList
	 */
	public void bulkInsertCourse(List<CourseStructureForm> contentsList);
}
