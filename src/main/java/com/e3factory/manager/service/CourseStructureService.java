package com.e3factory.manager.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.e3factory.common.dto.CourseContent;
import com.e3factory.common.dto.Lesson;
import com.e3factory.common.dto.Section;
import com.e3factory.common.form.CourseStructureForm;
import com.e3factory.common.repository.CourseStructureRepository;
import com.e3factory.common.repository.LessonRepository;
import com.e3factory.common.repository.SectionRepository;

@Service
public class CourseStructureService {
	@Autowired
	private CourseStructureRepository courseStructureRepository;
	@Autowired
	private SectionRepository sectionRepository;
	@Autowired
	private LessonRepository lessonRepository;

	public void getAllCourseView(Model model, int id) {
		//講座コンテンツを取得
		CourseContent courseContent = courseStructureRepository.findCourseStructureByCourseId(id);
		//セッションにセット
		model.addAttribute("courseContent", courseContent);
		model.addAttribute("courseId", id);
	}

	//コース内容を一括更新
	@Transactional
	public void bulkUpdateCourseContents(Model model, List<CourseStructureForm> structures) {
		int courseId = structures.get(0).getCourseId();
		//IDで一括削除
		courseStructureRepository.deleteCourseById(courseId);
		//セクションとレッスンの最大ID取得
		int nowSectionId = sectionRepository.getSectionCount();
		int nowLessonId = lessonRepository.getLessonCount();
		//コンテンツをソート
		Collections.sort(structures);
		//仮IDを本IDで確定させる
		int workId = 0;
		for( CourseStructureForm content: structures ) {
			//負数になっている仮セクションIDを設定
			if( content.getSectionId() < 0 ) {
				if(workId != content.getSectionId()) {
					//仮IDを保持
					workId = content.getSectionId();
					//新しいIDを付番
					content.setSectionId(++nowSectionId);
					//セクションテーブル追加
					Section section = new Section();
					section.setId(nowSectionId);
					section.setName(content.getSectionName() );
					sectionRepository.insert(section);
				} else {
					//同一セクションIDを付番
					content.setSectionId(nowSectionId);
				}
			}
			//負数になっている仮レッスンIDを設定
			if( content.getLessonId() < 0 ) {
				//新しいレッスンIDを付番
				content.setLessonId(++nowLessonId);
				//レッスンテーブル追加
				Lesson lesson = new Lesson();
				lesson.setId(nowLessonId);
				lesson.setTitle(content.getLessonTitle());
				lessonRepository.insert(lesson);
			}
		}
		//IDで一括登録
		courseStructureRepository.bulkInsertCourse(structures);
	}
}
