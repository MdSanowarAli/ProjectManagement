package com.infoistic.domain;

import java.util.Date;

public class User {

	private int id;
	private String userId;
	private String firstName;
	private String lastName;
	private String title;
	private String contactPhNo;
	private String emailId;
	private String password;
	private String status;
	private String securityCode;
	
	private String confirmPassword;
	private String oldPassword;
	
	private int createdBy;
	private Date createdAt;
	private String createdForm;
	private int updatedBy;
	private Date updatedAt;
	private String updatedForm;
	
	

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContactPhNo() {
		return this.contactPhNo;
	}

	public void setContactPhNo(String contactPhNo) {
		this.contactPhNo = contactPhNo;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSecurityCode() {
		return this.securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		//return createdAt;
		return new Date();
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
		//return updatedAt;
		return new Date();
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedForm() {
		return updatedForm;
	}

	public void setUpdatedForm(String updatedForm) {
		this.updatedForm = updatedForm;
	}

}