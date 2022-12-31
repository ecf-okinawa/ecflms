package com.e3factory.common.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CourseForm {
	private Integer id;
	@NotEmpty
	@Size (min = 1, max = 50)
	private String name;
	@NotEmpty
	private String userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
