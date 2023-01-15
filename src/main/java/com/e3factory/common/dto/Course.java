package com.e3factory.common.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * コース情報DTO
 * @author ecf_pc
 *
 */
@Getter
@Setter
public class Course {
	//コースID
	private Integer id;
	//コース名
	@NotEmpty
	@Size (min = 1, max = 50)
	private String name;
	//講師ユーザーID
	@NotEmpty
	private String userId;
	//イメージ画像パス
	private String imgPath;
	//講師名
	private String teacherName;
}