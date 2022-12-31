package com.e3factory.common.form;

import com.e3factory.common.dto.CourseStructure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseStructureForm extends CourseStructure {
	private String sectionName;
	private String lessonTitle;
}
