package com.e3factory.common.dto;

import javax.validation.constraints.NotEmpty;

public class Lesson {
	private Integer id;
	@NotEmpty
	private String title;
	private String content;
	private Integer order;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
}
