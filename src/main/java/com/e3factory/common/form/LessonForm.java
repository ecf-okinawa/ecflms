package com.e3factory.common.form;

import javax.validation.constraints.NotEmpty;

public class LessonForm {
	private Integer id;
	@NotEmpty
	private String title;
	private String content;

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
}
