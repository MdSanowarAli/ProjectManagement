package com.infoistic.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.infoistic.dao.DaoFactory;
import com.infoistic.dao.IProjectAssignDao;
import com.infoistic.domain.Project;
import com.infoistic.domain.ProjectAssign;
import com.infoistic.domain.User;
import com.infoistic.projectmanagement.Conversion;

public class ProjectAssignDao implements IProjectAssignDao {
	
	@Autowired
	IProjectAssignDao projectAssignDao;
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
		PreparedStatement pstmt;
		List<ProjectAssign> entities = new ArrayList<ProjectAssign>();
		String sql = "Select proAss.*, u.userId u_userId, u.firstName u_firstName, u.lastName u_lastName, p.projectId p_projectId, p.shortName p_shortName, p.startDate p_startDate from ProjectAssign proAss "
				+ " left join Users u on proAss.userId = u.id left join Project p on proAss.projectId= p.id ";
		
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		ResultSet row = pstmt.executeQuery();
		ProjectAssign entity;
		User user;
		Project project;
		while (row.next()) {
			entity = new ProjectAssign();
			user = new User();
			project= new Project();
			
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
	
			
			entities.add(entity);
		}
		return entities;
	}

	@Override
	public int Add(ProjectAssign entity) throws Exception {
		PreparedStatement pstmt;

		String sql = "INSERT INTO ProjectAssign (`userId`,\n" + 
				"`projectId`,\n" + 
				"`assignDate`,\n" + 
				"`estimatedEndDate`,\n" + 
				"`status`,\n" + 
				"`note`) VALUES(?,?,?,?,?,?)";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);

		pstmt.setInt(1, entity.getUserId());
		pstmt.setInt(2, entity.getProjectId());
		pstmt.setTimestamp(3, Conversion.getSqlTimestamp(entity.getAssignDate()));
		pstmt.setTimestamp(4, Conversion.getSqlTimestamp(entity.getEstimatedEndDate()));
		//pstmt.setTimestamp(5, Conversion.getSqlTimestamp(entity.getActualEndDate()));
		pstmt.setString(5, entity.getStatus());
		pstmt.setString(6, entity.getNote());
		

		pstmt.execute();
		

		sql = "Update Project set status = ?  where id=? ";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);

		pstmt.setString(1, "assigned");
		pstmt.setInt(2, entity.getProjectId());
		
		pstmt.execute();
		
		return 0;
	}

	@Override
	public void Update(ProjectAssign entity) throws Exception {
		PreparedStatement pstmt;

		String sql = "Update ProjectAssign set `estimatedEndDate` = ?,`actualEndDate` = ?, status = ? , note = concat (note,?)  where id=? ";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);

		/*pstmt.setInt(1, entity.getUserId());
		pstmt.setInt(2, entity.getProjectId());
		pstmt.setTimestamp(1, Conversion.getSqlTimestamp(entity.getAssignDate()));*/
		pstmt.setTimestamp(1, Conversion.getSqlTimestamp(entity.getEstimatedEndDate()));
		pstmt.setTimestamp(2, Conversion.getSqlTimestamp(entity.getActualEndDate()));
		pstmt.setString(3, entity.getStatus());
		pstmt.setString(4, ", "+entity.getNote()+"\n");
		pstmt.setInt(5, entity.getId());
		
		pstmt.execute();
		
	}

	@Override
	public void remove(int id) throws Exception {
		PreparedStatement pstmt;
		String sql = "Delete from  ProjectAssign  where id =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.execute();
		
	}
	
	

}
