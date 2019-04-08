package com.infoistic.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.infoistic.dao.DaoFactory;
import com.infoistic.dao.IToDoDao;
import com.infoistic.domain.Project;
import com.infoistic.domain.ProjectAssign;
import com.infoistic.domain.ToDo;
import com.infoistic.domain.User;
import com.infoistic.projectmanagement.Conversion;

public class ToDoDao implements IToDoDao {

	

	@Override
	public List<ProjectAssign> getByUserId(int userId) throws Exception {
		PreparedStatement pstmt;
		List<ProjectAssign> entities = new ArrayList<ProjectAssign>();
		String sql = "Select proAss.*, u.userId u_userId, u.firstName u_firstName, u.lastName u_lastName, p.projectId p_projectId, p.shortName p_shortName, p.startDate p_startDate from ProjectAssign proAss "
				+ " left join Users u on proAss.userId = u.id left join Project p on proAss.projectId= p.id where u.id =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet row = pstmt.executeQuery();
		ProjectAssign entity;
		User user;
		Project project;
		ProjectAssignDao pa;
		while (row.next()) {
			entity= new ProjectAssign();
			user= new User();
			project= new Project();
			
			entity.setId(row.getInt("id"));
			entity.setUserId(row.getInt("userId"));
			entity.setProjectId(row.getInt("projectId"));
	
			entity.setAssignDate(row.getDate("assignDate"));
			entity.setEstimatedEndDate(row.getDate("estimatedEndDate"));
			entity.setActualEndDate(row.getDate("actualEndDate"));
			entity.setNote(row.getString("note"));
			entity.setStatus(row.getString("status"));
			
			
			user.setUserId(row.getString("u_userId"));
			user.setFirstName(row.getString("u_firstName"));
			user.setLastName(row.getString("u_lastName"));
			entity.setUser(user);
			
			project.setProjectId(row.getString("p_projectId"));
			project.setShortName(row.getString("p_shortName"));
			project.setStartDate(row.getDate("p_startDate"));
			
			entity.setProject(project);
			
			entities.add(entity);
		}
		return entities;
	}

	@Override
	public ProjectAssign getById(int Id) throws Exception {
		PreparedStatement pstmt;
		String sql = "Select proAss.*, u.userId u_userId, u.firstName u_firstName, u.lastName u_lastName, p.projectId p_projectId, p.shortName p_shortName, p.startDate p_startDate from ProjectAssign proAss "
				+ " left join Users u on proAss.userId = u.id left join Project p on proAss.projectId= p.id where proAss.id =? ";
		
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, Id);
		ResultSet row = pstmt.executeQuery();
		ProjectAssign entity= new ProjectAssign();
		User user = new User();
		Project project = new Project();
		while (row.next()) {
			entity.setId(row.getInt("id"));
			entity.setUserId(row.getInt("userId"));
			entity.setProjectId(row.getInt("projectId"));
		
			entity.setAssignDate(row.getDate("assignDate"));
			entity.setEstimatedEndDate(row.getDate("estimatedEndDate"));
			entity.setActualEndDate(row.getDate("actualEndDate"));
			entity.setStatus(row.getString("status"));
			entity.setNote(row.getString("note"));
			
			user.setUserId(row.getString("u_userId"));
			user.setFirstName(row.getString("u_firstName"));
			user.setLastName(row.getString("u_lastName"));
			entity.setUser(user);
			
			project.setProjectId(row.getString("p_projectId"));
			project.setShortName(row.getString("p_shortName"));
			project.setStartDate(row.getDate("p_startDate"));
			entity.setProject(project);
			
		}
		return entity;
	}

	@Override
	public List<ProjectAssign> get() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int Add(ProjectAssign entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void Update(ProjectAssign entity) throws Exception {
		PreparedStatement pstmt;

		Connection conn;
		conn=DaoFactory.getDefaultConn();
		conn.setAutoCommit(false);
		
		String sql = "Update ProjectAssign set status = ? , note = concat (note,?)  where id=? ";

		pstmt =conn.prepareStatement(sql);

		//pstmt.setTimestamp(1, Conversion.getSqlTimestamp(entity.getActualEndDate()));
		pstmt.setString(1, entity.getStatus());
		pstmt.setString(2, ", "+entity.getNote()+"\n");
		pstmt.setInt(3, entity.getId());
		
		pstmt.execute();
	
		System.out.println(entity.getStatus());
		
		if ( entity.getStatus().equals("Close")) {
			sql = "Update Project set status = ?  where Id=? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "Done");
			pstmt.setInt(2, entity.getProjectId());
			
			pstmt.execute();
			
			sql = "Update ProjectAssign set `actualEndDate` = ?  where id=? ";
			Date dt= new Date();
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, Conversion.getSqlTimestamp(dt));
			pstmt.setInt(2, entity.getId());
			
			pstmt.execute();
		}
		else {
			System.out.println("Not Closed");
		}
		conn.commit();
	}

	@Override
	public void remove(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	//for Search
	/*@Override
	public List<ProjectAssign> get(String searchString) throws Exception {
		PreparedStatement pstmt;
		List<ProjectAssign> entities = new ArrayList<ProjectAssign>();
		String sql = "Select * from ProjectAssign where estimatedEndDate like ?";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setString(1, "%"+searchString+"%");
		ResultSet row = pstmt.executeQuery();
		ProjectAssign entity;
		while (row.next()) {
			entity = new ProjectAssign();
			entity.setId(row.getInt("id"));
			entity.setUserId(row.getInt("userId"));
			entity.setProjectId(row.getInt("projectId"));
		
			entity.setAssignDate(row.getDate("assignDate"));
			entity.setEstimatedEndDate(row.getDate("estimatedEndDate"));
			entity.setActualEndDate(row.getDate("actualEndDate"));
			entity.setStatus(row.getString("status"));
			entity.setNote(row.getString("note"));
			entities.add(entity);
		}
		return entities;
	}*/
	
	@Override
	public List<ProjectAssign> getSearch(Date estimatedEndDate, String status)
			throws Exception {

		PreparedStatement pstmt;
		List<ProjectAssign> entities = new ArrayList<ProjectAssign>();
		ProjectAssign entity;
		String sql = "SELECT pa.* FROM ProjectAssign pa right join  (SELECT max(id) id FROM ProjectAssign where 1= 1  ";
		if (estimatedEndDate != null) {
			sql += " and estimatedEndDate >= ? ";
		} else {
			sql += " and 1 = ? ";
		}
		
		if (!status.equals("")) {
			sql += " and ( status like ? )";
		}
		sql += " group by projectId";
		
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		System.out.println(sql);

		if (estimatedEndDate != null) {
			pstmt.setTimestamp(1, Conversion.getSqlTimestamp(estimatedEndDate));
		} else {
			pstmt.setInt(1, 1);
		}
		
		if (!status.equals("")) {
			pstmt.setString(4, "%" + status + "%");
			pstmt.setString(5, "%" + status + "%");
		}
		ResultSet row = pstmt.executeQuery();
		while (row.next()) {
			entity = new ProjectAssign();
			entity.setId(row.getInt("id"));
			entity.setUserId(row.getInt("userId"));
			entity.setProjectId(row.getInt("projectId"));
	
			entity.setAssignDate(row.getDate("assignDate"));
			entity.setEstimatedEndDate(row.getDate("estimatedEndDate"));
			entity.setActualEndDate(row.getDate("actualEndDate"));
			entity.setNote(row.getString("note"));
			entity.setStatus(row.getString("status"));
			entities.add(entity);
		}
		return entities;
	}




}
