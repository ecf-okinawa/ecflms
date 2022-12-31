package com.e3factory.common.dto;

import lombok.Data;

@Data
public class CourseStructure implements Comparable<CourseStructure> {
	private Integer courseId;
	private Integer orderNo;
	private Integer sectionId;
	private Integer lessonId;

	@Override
	public int compareTo(CourseStructure o) {
		return this.orderNo - o.orderNo;
	}
}
