package com.e3factory.user.service;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3factory.common.dto.Lesson;
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
	public Lesson viewLesson(int id) {
		Lesson lesson = lessonRepository.getLesson(id);
		lesson.setContent(StringEscapeUtils.unescapeHtml4(lesson.getContent()));
		return lesson;
	}
}
