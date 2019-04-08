package com.infoistic.dao;


import com.infoistic.domain.AuthUser;
import com.infoistic.domain.User;



public interface IUserDao extends IRepository<User> {
	
	AuthUser login(String userId) throws Exception;
	
	void PasswordUpdate(User entity) throws Exception;
	
	void ResetpasswordUpdate(User entity) throws Exception;
	
	void passwordResetRequest(User entity) throws Exception;
}