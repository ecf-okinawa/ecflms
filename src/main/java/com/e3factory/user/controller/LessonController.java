package com.e3factory.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3factory.common.dto.Lesson;
import com.e3factory.user.service.LessonService;

@Controller
public class LessonController {
	@Autowired
	private LessonService service;

	/**
	 * 講義プレビュー
	 * @param model
	 * @param lesson
	 * @return
	 */
	@RequestMapping("/u/lesson/view/{courseId}/{lessonId}")
	public String viewLesson(Model model, @PathVariable int courseId, @PathVariable int lessonId) {
		//講座情報を取得
		Lesson lesson = service.viewLesson(lessonId);
		//セッションにセット
		model.addAttribute("lesson", lesson);
		model.addAttribute("courseId", courseId);
		return "user/lesson/view";

	}
}
