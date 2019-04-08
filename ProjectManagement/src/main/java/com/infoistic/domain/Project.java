package com.infoistic.domain;

import java.util.Date;
import java.util.List;

import com.infoistic.projectmanagement.Conversion;

public class Project {

	private int id;
	private String projectId;
	private String shortName;
	private String description;
	private Date startDate;
	private String status;
	private String note;
	private String attachment;

	private String startDateS;

	private List<Attachment> attachments;

	public Project() {
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartDateS() {
		return Conversion.dateToString(startDate);
	}

	public void setStartDateS(String startDateS) {
		this.startDateS = startDateS;
		this.startDate = Conversion.stringToDate(startDateS);
	}

}
