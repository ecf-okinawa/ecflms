package com.e3factory.manager.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3factory.common.dto.Lesson;
import com.e3factory.common.form.LessonForm;
import com.e3factory.manager.service.LessonService;

@Controller
public class LessonController {
	@Autowired
	private LessonService service;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 講義編集画面表示
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/m/lesson/update/{id}")
	public String editLesson(Model model, @PathVariable int id) {
		//講座情報を取得
		Lesson lesson = service.getLesson(id);

		//セッションにセット
		model.addAttribute("lesson", lesson);
		return "manager/lesson/update";
	}

	/**
	 * 講義プレビュー
	 * @param model
	 * @param lesson
	 * @return
	 */
	@RequestMapping("/m/lesson/preview/{id}")
	public String updateLesson(Model model, Lesson lesson) {
		//セッションにセット
		model.addAttribute("lesson", lesson);
		return "manager/lesson/preview";
	}

	/**
	 * 講義更新処理
	 * @param model
	 * @param lesson
	 * @return
	 */
	@RequestMapping("/m/lesson/doupdate")
	public String previewLesson(Model model, @Validated LessonForm form) {
		//バリデーション(LessonFormで定義済み)
		//更新処理
		service.updateLesson(form);
		//メッセージ入れる
		String message = messageSource.getMessage("i.lesson.001", new String[] {}, Locale.getDefault());
		model.addAttribute("message", message);
		model.addAttribute("lesson", form);
		return "manager/lesson/update";
	}
}
