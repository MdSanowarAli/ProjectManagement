package com.infoistic.domain;

import java.util.Date;

import com.infoistic.projectmanagement.Conversion;

public class ToDo {

	private int id;
	private int userId;
	private int projectId;

	private Date startDate;
	private Date endDate;
	private String note;

	private String startDateS;
	private String endDateS;

	private User user;
	private Project project;

	public ToDo() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getStartDateS() {
		return Conversion.dateToString(startDate);
	}

	public void setStartDateS(String startDateS) {
		this.startDateS = startDateS;
		this.startDate = Conversion.stringToDate(startDateS);
	}

	public String getEndDateS() {
		return Conversion.dateToString(endDate);
	}

	public void setEndDateS(String endDateS) {
		this.endDateS = endDateS;
		this.endDate = Conversion.stringToDate(endDateS);
	}

}
