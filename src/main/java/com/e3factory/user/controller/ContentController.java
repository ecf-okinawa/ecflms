package com.e3factory.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3factory.common.form.CourseForm;
import com.e3factory.user.service.CourseStructureService;

@Controller
public class ContentController {
	@Autowired
	private CourseStructureService service;

	@ModelAttribute
	public CourseForm createForm() {
		return new CourseForm();
	}

	//講座内容一覧
	@RequestMapping("/u/course/content/view/{id}")
	public String courseContentsView(Model model, @PathVariable int id) {
		service.getAllCourseView(model, id);
		//ビュー指定
		return "user/courseContents/view";
	}
}
