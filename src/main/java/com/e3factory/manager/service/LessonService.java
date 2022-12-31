package com.e3factory.manager.service;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e3factory.common.dto.Lesson;
import com.e3factory.common.form.LessonForm;
import com.e3factory.common.repository.LessonRepository;

@Service
public class LessonService {
	@Autowired
	private LessonRepository lessonRepository;

	/**
	 * 講義情報を取得
	 * @param id 講義ID
	 * @return
	 */
	public Lesson getLesson(int id) {
		Lesson lesson = lessonRepository.getLesson(id);
		lesson.setContent(StringEscapeUtils.unescapeHtml4(lesson.getContent()));
		return lesson;
	}

	/**
	 * 講義情報を更新
	 * @param lesson
	 */
	@Transactional
	public void updateLesson(LessonForm lesson) {
		//DTOへ移動
		Lesson dto = new Lesson();
		dto.setId(lesson.getId());
		dto.setTitle(lesson.getTitle());
		dto.setContent(lesson.getContent());
		//講義内容HTMLのサニタイズ
		dto.setContent(StringEscapeUtils.escapeHtml4(dto.getContent()));
		//講義の更新
		lessonRepository.update(dto);
	}
}
