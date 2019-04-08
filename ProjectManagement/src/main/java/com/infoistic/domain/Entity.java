package com.infoistic.domain;

import java.util.Date;

import com.infoistic.projectmanagement.*;;

public class Entity {

	private int Id;

	private int createdBy;
	private Date createdAt;
	private String createdAtS;
	private String createdForm;
	
	private int updatedBy;
	private Date updatedAt;
	private String updatedAtS;
	private String updatedForm;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAtS() {
		return Conversion.dateToString(createdAt);
	}

	public void setCreatedAtS(String createdAtS) {
		this.createdAtS = createdAtS;
		this.createdAt = Conversion.stringToDate(createdAtS);
	}

	public String getCreatedForm() {
		return createdForm;
	}

	public void setCreatedForm(String createdForm) {
		this.createdForm = createdForm;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAtS() {
		return Conversion.dateToString(updatedAt);
	}

	public void setUpdatedAtS(String updatedAtS) {
		this.updatedAtS = updatedAtS;
		this.updatedAt = Conversion.stringToDate(updatedAtS);
	}

	public String getUpdatedForm() {
		return updatedForm;
	}

	public void setUpdatedForm(String updatedForm) {
		this.updatedForm = updatedForm;
	}

}
