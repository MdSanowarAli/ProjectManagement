package com.infoistic.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.infoistic.dao.DaoFactory;
import com.infoistic.dao.IProjectDao;
import com.infoistic.domain.Attachment;
import com.infoistic.domain.Project;
import com.infoistic.projectmanagement.Conversion;
//import com.mysql.jdbc.Statement;

public class ProjectDao implements IProjectDao {

	@Override
	public Project getById(int Id) throws Exception {
		PreparedStatement pstmt;
		String sql = "Select * from Project where id =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, Id);
		ResultSet row = pstmt.executeQuery();
		Project entity= new Project();
		while (row.next()) {
			entity.setId(row.getInt("id"));
			entity.setProjectId(row.getString("projectId"));
			entity.setShortName(row.getString("shortName"));
			entity.setDescription(row.getString("description"));
			entity.setStartDate(row.getDate("startDate"));
			entity.setStatus(row.getString("status"));
			entity.setNote(row.getString("note"));
			entity.setAttachment(row.getString("attachment"));
		}
		return entity;
		
	}

	@Override
	public List<Project> get() throws Exception {
		PreparedStatement pstmt;
		List<Project> entities = new ArrayList<Project>();
		String sql = "Select * from Project";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		ResultSet row = pstmt.executeQuery();
		Project entity;
		while (row.next()) {
			entity = new Project();
			entity.setId(row.getInt("id"));
			entity.setProjectId(row.getString("projectId"));
			entity.setShortName(row.getString("shortName"));
			entity.setDescription(row.getString("description"));
			entity.setStartDate(row.getDate("startDate"));
			entity.setStatus(row.getString("status"));
			entity.setNote(row.getString("note"));
			entity.setAttachment(row.getString("attachment"));
			
			entities.add(entity);
		}
		return entities;
	}

	@Override
	public int Add(Project entity) throws Exception {
		PreparedStatement pstmt;

		String sql = "INSERT INTO Project (`projectId`,`shortName`,`description`,`startDate`,`status`,`note`,`attachment`) VALUES(?,?,?,?,?,?,?)";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);

		pstmt.setString(1, entity.getProjectId());
		pstmt.setString(2, entity.getShortName());
		pstmt.setString(3, entity.getDescription());
		pstmt.setTimestamp(4, Conversion.getSqlTimestamp(entity.getStartDate()));
		pstmt.setString(5, entity.getStatus());
		pstmt.setString(6, entity.getNote());
		pstmt.setString(7, entity.getAttachment());
		

		pstmt.execute();
		return 0;
	}

	@Override
	public void Update(Project entity) throws Exception {
		PreparedStatement pstmt;

		String sql = "Update Project set projectId =? , shortName = ? , description =? , startDate =? , status = ? , note = concat (note,?)  where id=? ";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);

		pstmt.setString(1, entity.getProjectId());
		pstmt.setString(2, entity.getShortName());
		pstmt.setString(3, entity.getDescription());
		pstmt.setTimestamp(4, Conversion.getSqlTimestamp(entity.getStartDate()));
		pstmt.setString(5, entity.getStatus());
		pstmt.setString(6, ", "+entity.getNote()+"\n");
		//pstmt.setString(7, entity.getAttachment());
		pstmt.setInt(7, entity.getId());
		
		pstmt.execute();
	}

	@Override
	public void remove(int id) throws Exception {
		PreparedStatement pstmt;
		String sql = "Delete from  Project  where id =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.execute();
		
	}

	@Override
	public void AddwithFile(Project entity) throws Exception {
		Connection conn = DaoFactory.getDefaultConn();
		try {
			PreparedStatement pstmt;
			String sql ="Insert into Project (projectId, shortName, description, startDate, status, note, attachment) "
					+ "values(?,?,?,?,?,?,?)";

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, entity.getProjectId());
			pstmt.setString(2, entity.getShortName());
			pstmt.setString(3, entity.getDescription());
			pstmt.setTimestamp(4, Conversion.getSqlTimestamp(entity.getStartDate()));
			pstmt.setString(5, entity.getStatus());
			pstmt.setString(6, entity.getNote());
			pstmt.setString(7, entity.getAttachment());
			

			pstmt.executeUpdate();
			ResultSet keys = pstmt.getGeneratedKeys();
			keys.next();
			int key = keys.getInt(1);

			AttachmentDao attachmentDao = new AttachmentDao();

			for (Attachment attachment : entity.getAttachments()) {
				if (!attachment.getFilePath().equals("")) {
					attachment.setRefId(key);
					attachmentDao.AddFromAnother(attachment, conn);
				}
			}
			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception(ex.getMessage());
		}
		
	}

	
	boolean haveItem(List<Attachment> newAttachment, Attachment attachment) {
		if (newAttachment != null) {
			for (Attachment item : newAttachment) {
				if (attachment.getId() == item.getId()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<Attachment> UpdatewithFile(Project entity, List<Attachment> attachmentsNew) throws Exception {
		AttachmentDao attachmentDao = new AttachmentDao();
		List<Attachment> oldAttachmentDB = attachmentDao.get(Conversion.PROJECT_ATT, entity.getId());
		List<Attachment> newAttachment = entity.getAttachments();
		List<Attachment> attachmentRemove = new ArrayList<Attachment>();

		Connection conn = DaoFactory.getDefaultConn();
		conn.setAutoCommit(false);
		PreparedStatement pstmt;

		for (Attachment attachment : oldAttachmentDB) {
			if (!haveItem(newAttachment, attachment)) {
				attachmentRemove.add(attachment);
				attachmentDao.remove(attachment.getId());
			}
		}
		try {

			String sql = "Update Project  set projectId =?, shortName=?, description=?, startDate=?, status=?, note=?, attachment=? where id =?";

			pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
			pstmt.setString(1, entity.getProjectId());
			pstmt.setString(2, entity.getShortName());
			pstmt.setString(3, entity.getDescription());
			pstmt.setTimestamp(4, Conversion.getSqlTimestamp(entity.getStartDate()));
			pstmt.setString(5, entity.getStatus());
			pstmt.setString(6, entity.getNote());
			pstmt.setString(7, entity.getAttachment());
			pstmt.setInt(8, entity.getId());
			pstmt.execute();

			for (Attachment attachment : attachmentsNew) {
				if (!attachment.getFilePath().equals("")) {
					attachment.setRefId(entity.getId());
					attachmentDao.AddFromAnother(attachment, conn);
				}
			}
			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception(ex.getMessage());
		}
		return attachmentRemove;
	}

	@Override
	public List<Project> get(String searchString) throws Exception {
		PreparedStatement pstmt;
		List<Project> entities = new ArrayList<Project>();
		String sql = "Select * from Project where shortName like ?";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setString(1, "%" + searchString + "%");
		ResultSet row = pstmt.executeQuery();
		Project entity;
		while (row.next()) {
			entity = new Project();
			entity.setId(row.getInt("id"));
			entity.setShortName(row.getString("shortName"));
			entities.add(entity);
		}
		return entities;
	}

}
