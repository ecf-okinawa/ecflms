package com.e3factory.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3factory.common.form.CourseStructureForm;
import com.e3factory.manager.service.CourseStructureService;

@Controller
public class ContentController {
	@Autowired
	private CourseStructureService service;

	/**
	 * 講座一覧を表示する
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/m/course/content/update/{id}")
	public String courseAllView(Model model, @PathVariable int id) {
		//ロジック処理
		service.getAllCourseView(model, id);
		return "manager/courseContents/update";
	}

	/**
	 * 講座内容を更新する
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping("/m/course/content/doupdate")
	@ResponseBody
	public String courseUpdate(Model model, @RequestBody List<CourseStructureForm> form) {
		service.bulkUpdateCourseContents(model, form);
		return "OK";
	}
}
