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
	@NotEmpty(message="{valid.w01}")
	@Size (min = 1, max = 50, message="{valid.w03}")
	private String name;
	//講師ユーザーID
	@NotEmpty(message="{valid.w01}")
	@Size (min = 1, max = 20, message="{valid.w03}")
	private String userId;
	//イメージ画像パス
	private String imgPath;
	//講師名
	private String teacherName;
}