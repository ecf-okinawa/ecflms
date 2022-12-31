package com.e3factory.common.dto;

import java.util.ArrayList;
import java.util.List;

public class Section {
	private Integer id;
	private String name;
	private List<Lesson> lessons;
	private Integer order;

	public Section() {
		this.lessons = new ArrayList<Lesson>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
