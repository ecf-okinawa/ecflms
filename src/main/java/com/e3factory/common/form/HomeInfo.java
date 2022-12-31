package com.e3factory.common.form;

import lombok.Data;

@Data
public class HomeInfo {
	//コースID
	private Integer courseId;
	//コース名
	private String courseName;
	//講師名
	private String teacherName;
	//コースイメージ画像パス
	private String imgPath;
	//参加状況
	private boolean attendance;
}
