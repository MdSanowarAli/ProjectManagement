package com.infoistic.domain;

import java.util.Date;

import com.infoistic.projectmanagement.Conversion;

public class ProjectAssign implements java.io.Serializable {

	private int id;
	private int userId;
	private int projectId;
	private Date assignDate;
	private Date estimatedEndDate;
	private Date actualEndDate;
	private String status;
	private String note;

	private String assignDateS;
	private String estimatedEndDateS;
	private String actualEndDateS;

	private User user;
	private Project project;

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

	public ProjectAssign() {
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



	public Date getEstimatedEndDate() {
		return estimatedEndDate;
	}

	public void setEstimatedEndDate(Date estimatedEndDate) {
		this.estimatedEndDate = estimatedEndDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getAssignDate() {
		return new Date();
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public String getAssignDateS() {
		return Conversion.dateToString(assignDate);
	}

	public void setAssignDateS(String assignDateS) {
		this.assignDateS = assignDateS;
		this.assignDate = Conversion.stringToDate(assignDateS);
	}

	public String getEstimatedEndDateS() {
		return Conversion.dateToString(estimatedEndDate);
	}

	public void setEstimatedEndDateS(String estimatedEndDateS) {
		this.estimatedEndDateS = estimatedEndDateS;
		this.estimatedEndDate = Conversion.stringToDate(estimatedEndDateS);

	}

	public String getActualEndDateS() {
		return Conversion.dateToString(actualEndDate);
	}

	public void setActualEndDateS(String actualEndDateS) {
		this.actualEndDateS = actualEndDateS;
		this.actualEndDate = Conversion.stringToDate(actualEndDateS);
	}

}
