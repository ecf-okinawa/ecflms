package com.e3factory.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.e3factory.common.dto.CourseContent;
import com.e3factory.common.repository.CourseStructureRepository;

@Service
public class CourseStructureService {
	@Autowired
	private CourseStructureRepository courseStructureRepository;

	public void getAllCourseView(Model model, int id) {
		//講座コンテンツを取得
		CourseContent courseContent = courseStructureRepository.findCourseStructureByCourseId(id);
		//セッションにセット
		model.addAttribute("courseContent", courseContent);
		model.addAttribute("courseId", id);
	}
}
