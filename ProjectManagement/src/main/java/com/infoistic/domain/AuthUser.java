package com.infoistic.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthUser extends User{


	public AuthUser(String username, String password,Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
