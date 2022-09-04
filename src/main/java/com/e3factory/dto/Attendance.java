package com.e3factory.dto;

public class Attendance {
	//受講ID
	private Integer id;
	//ユーザーID
	private String userId;
	//ユーザー名
	private String userName;
	//講座ID
	private Integer courseId;
	//講座名
	private String courseName;
	//得点
	private Integer score;
	//ランク
	private String rank;

	/**
	 * 受講IDを返す
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 受講IDを設定する
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * ユーザーIDを返す
	 * @return
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * ユーザーIDを設定する
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * ユーザー名を返す
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * ユーザー名を設定する
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 講座IDを返す
	 * @return
	 */
	public Integer getCourseId() {
		return courseId;
	}
	/**
	 * 講座IDを設定する
	 * @param courseId
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	/**
	 * 講座名を返す
	 * @return
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * 講座名を設定する
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * 得点を返す
	 * @return
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 得点を設定する
	 * @param score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * ランクを返す
	 * @return
	 */
	public String getRank() {
		return rank;
	}
	/**
	 * ランクを設定する
	 * @param rank
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
}
