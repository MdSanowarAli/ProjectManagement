package com.infoistic.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.infoistic.dao.DaoFactory;
import com.infoistic.dao.IUserDao;
import com.infoistic.domain.AuthUser;
import com.infoistic.domain.User;
import com.infoistic.projectmanagement.Conversion;

public class UserDao implements IUserDao {

	@Override
	public User getById(int Id) throws Exception {
		PreparedStatement pstmt;
		String sql = "Select * from Users where id =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, Id);
		ResultSet row = pstmt.executeQuery();
		User entity = new User();
		while (row.next()) {
			entity.setId(row.getInt("id"));
			entity.setUserId(row.getString("userId"));
			entity.setFirstName(row.getString("firstName"));
			entity.setLastName(row.getString("lastName"));
			entity.setTitle(row.getString("title"));
			entity.setContactPhNo(row.getString("contactPhNo"));
			entity.setEmailId(row.getString("emailId"));
			entity.setPassword(row.getString("password"));
			entity.setStatus(row.getString("status"));
			entity.setSecurityCode(row.getString("securityCode"));

			entity.setCreatedBy(row.getInt("createdBy"));
			entity.setCreatedAt(row.getDate("createdAt"));
			entity.setCreatedForm(row.getString("createdForm"));
			entity.setUpdatedBy(row.getInt("updatedBy"));
			entity.setUpdatedAt(row.getDate("updatedAt"));
			entity.setUpdatedForm(row.getString("updatedForm"));
		}
		return entity;
	}

	@Override
	public List<User> get() throws Exception {
		PreparedStatement pstmt;
		List<User> entities = new ArrayList<User>();
		String sql = "Select * from Users";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		ResultSet row = pstmt.executeQuery();
		User entity;
		while (row.next()) {
			entity = new User();
			entity.setId(row.getInt("id"));
			entity.setUserId(row.getString("userId"));
			entity.setFirstName(row.getString("firstName"));
			entity.setLastName(row.getString("lastName"));
			entity.setTitle(row.getString("title"));
			entity.setContactPhNo(row.getString("contactPhNo"));
			entity.setEmailId(row.getString("emailId"));
			entity.setPassword(row.getString("password"));
			entity.setStatus(row.getString("status"));
			entity.setSecurityCode(row.getString("securityCode"));

			entity.setCreatedBy(row.getInt("createdBy"));
			entity.setCreatedAt(row.getDate("createdAt"));
			entity.setCreatedForm(row.getString("createdForm"));
			entity.setUpdatedBy(row.getInt("updatedBy"));
			entity.setUpdatedAt(row.getDate("updatedAt"));
			entity.setUpdatedForm(row.getString("updatedForm"));

			entities.add(entity);
		}
		return entities;
	}

	@Override
	public int Add(User entity) throws Exception {
		PreparedStatement pstmt;

		/*
		 * String sql = "Select count(*) count from Insurances where userId =?"; pstmt =
		 * DaoFactory.getDefaultConn().prepareStatement(sql); pstmt.setString(1,
		 * entity.getUserId()); ResultSet row = pstmt.executeQuery(); if (row.next()) {
		 * if (row.getInt("count") > 0) { throw new Exception("'" + entity.getUserId() +
		 * "' already exist please try another one."); } }
		 */

		String sql = "INSERT INTO Users (`userId`,`firstName`,`lastName`,`title`,`contactPhNo`,`emailId`,`password`,`status`) VALUES(?,?,?,?,?,?,?,?)";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);

		pstmt.setString(1, entity.getUserId());
		pstmt.setString(2, entity.getFirstName());
		pstmt.setString(3, entity.getLastName());
		pstmt.setString(4, entity.getTitle());
		pstmt.setString(5, entity.getContactPhNo());
		pstmt.setString(6, entity.getEmailId());
		pstmt.setString(7, entity.getPassword());
		pstmt.setString(8, entity.getStatus());

		/*
		 * pstmt.setInt(10, entity.getCreatedBy()); pstmt.setDate(11,
		 * entity.getCreatedAt()); pstmt.setString(12, entity.getCreatedForm());
		 */

		pstmt.execute();
		return 0;
	}

	@Override
	public void Update(User entity) throws Exception {
		PreparedStatement pstmt;

		String sql = "Update Users set userId = ?, firstName = ?, lastName = ?, title = ?, contactPhNo = ?, emailId = ?, status = ? WHERE id = ? ";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);

		pstmt.setString(1, entity.getUserId());
		pstmt.setString(2, entity.getFirstName());
		pstmt.setString(3, entity.getLastName());
		pstmt.setString(4, entity.getTitle());
		pstmt.setString(5, entity.getContactPhNo());
		pstmt.setString(6, entity.getEmailId());
		// pstmt.setString(7, entity.getPassword());
		pstmt.setString(7, entity.getStatus());
		pstmt.setInt(8, entity.getId());

		/*
		 * pstmt.setInt(10, entity.getUpdatedBy()); pstmt.setDate(11,
		 * entity.getUpdatedAt()); pstmt.setString(12, entity.getUpdatedForm());
		 */

		pstmt.execute();

	}

	@Override
	public void remove(int id) throws Exception {
		PreparedStatement pstmt;
		String sql = "Delete from  Users  where id =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.execute();

	}
	
	@Override
	public AuthUser login(String userId) throws Exception {
		PreparedStatement pstmt;
		String sql = "Select * from Users where userId =?";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setString(1, userId);
		ResultSet row = pstmt.executeQuery();
		AuthUser entity = null;
		if (row.next()) {
			entity = new AuthUser(row.getString("userId"),row.getString("password"), getAuthority("ROLE_ROLE"));
			entity.setUserId(row.getInt("id"));
		}
		return entity;
	}
/*	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login") 
				.permitAll();        
	}*/
	
	@Override
	public void PasswordUpdate(User entity) throws Exception {
		PreparedStatement pstmt;
		String sql = "Update Users set " + "password =?   where Id =? and password =? ";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setString(1, entity.getPassword());
		pstmt.setInt(2, entity.getId());
		pstmt.setString(3, entity.getOldPassword());
		pstmt.execute();

	}
	
	@Override
	public void ResetpasswordUpdate(User entity) throws Exception {
		PreparedStatement pstmt;
		String sql = "Update Users set  password =? ,securityCode =?  where emailId =? and securityCode =? ";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setString(1, Conversion.encrypt(entity.getPassword()));
		pstmt.setString(2, "rdghsdfjidvh");
		pstmt.setString(3, entity.getEmailId());
		pstmt.setString(4, entity.getSecurityCode());
		pstmt.execute();

	}

	@Override
	public void passwordResetRequest(User entity) throws Exception {
		PreparedStatement pstmt;
		String sql = "Update Users set securityCode =?   where emailId =? ";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setString(1, entity.getSecurityCode());
		pstmt.setString(2, entity.getEmailId());

		pstmt.execute();

	}

	private List<GrantedAuthority> getAuthority(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}
}
