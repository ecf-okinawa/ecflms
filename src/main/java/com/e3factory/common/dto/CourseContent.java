package com.e3factory.common.dto;

import java.util.List;

import lombok.Data;

@Data
public class CourseContent{
	private Integer id;
	private String name;
	private List<Section> sections;
}
