package com.infoistic.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.infoistic.projectmanagement.*;
import com.infoistic.dao.DaoFactory;
import com.infoistic.dao.IAttachmentDao;
import com.infoistic.domain.Attachment;


public class AttachmentDao implements IAttachmentDao {

	@Override
	public Attachment getById(int id) throws Exception {
		PreparedStatement pstmt;
		String sql = "Select * from Attachments where id =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet row = pstmt.executeQuery();
		Attachment entity = new Attachment();
		if (row.next()) {
			entity.setRefTable(row.getString("refTable"));
			entity.setRefId(row.getInt("refId"));
			entity.setFilePath(row.getString("filePath"));
			entity.setFileName(row.getString("fileName"));
			
			entity.setId(row.getInt("id"));
			
		/*	entity.setCreatedBy(row.getInt("createdBy"));
			entity.setCreatedAt(row.getDate("createdAt"));
			entity.setCreatedForm(row.getString("createdForm"));
			entity.setUpdatedBy(row.getInt("updatedBy"));
			entity.setUpdatedAt(row.getDate("updatedAt"));
			entity.setUpdatedForm(row.getString("updatedForm"));*/
			
		}
		return entity;
	}
	@Override
	public List<Attachment> get() throws Exception {
		return null;
	}


	public List<Attachment> get(String refTable, int refId) throws Exception {
		PreparedStatement pstmt;
		List<Attachment> entities = new ArrayList<Attachment>();
		String sql = "Select * from Attachments where refTable = ? and refId =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setString(1, refTable);
		pstmt.setInt(2, refId);
		ResultSet row = pstmt.executeQuery();
		Attachment entity;
		while (row.next()) {
			entity = new Attachment();
			entity.setRefTable(row.getString("refTable"));
			entity.setRefId(row.getInt("refId"));
			entity.setFilePath(row.getString("filePath"));
			entity.setFileName(row.getString("fileName"));
			entity.setId(row.getInt("id"));
			
		/*	entity.setCreatedBy(row.getInt("createdBy"));
			entity.setCreatedAt(row.getDate("createdAt"));
			entity.setCreatedForm(row.getString("createdForm"));
			entity.setUpdatedBy(row.getInt("updatedBy"));
			entity.setUpdatedAt(row.getDate("updatedAt"));
			entity.setUpdatedForm(row.getString("updatedForm"));*/
			
			entities.add(entity);
		}
		return entities;
	}

	@Override
	public int  AddFromAnother(Attachment entity , Connection conn) throws Exception {
		PreparedStatement pstmt;

		String sql = "INSERT INTO Attachments (`refTable`,`refId`,`filePath`,fileName)VALUES (?,?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, entity.getRefTable());
		pstmt.setInt(2, entity.getRefId());
		pstmt.setString(3, entity.getFilePath());
		pstmt.setString(4, entity.getFileName());
		/*pstmt.setInt(5, entity.getCreatedBy());
		pstmt.setTimestamp(6, Conversion.getSqlTimestamp(entity.getCreatedAt()));
		pstmt.setString(7, entity.getCreatedForm());*/
		pstmt.execute();
		return 0;
	}

	@Override
	public void Update(Attachment entity) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;

		String sql = "Update  Attachments  set filePath=? , fileName =? , updatedBy =? , updatedAt =? , updatedForm=?  where id =? ";

		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setString(1, entity.getFilePath());
		pstmt.setString(2, entity.getFileName());
		pstmt.setInt(3, entity.getUpdatedBy());
		pstmt.setTimestamp(4, Conversion.getSqlTimestamp(entity.getUpdatedAt()));
		pstmt.setString(5, entity.getUpdatedForm());
		pstmt.setInt(6, entity.getId());
		pstmt.execute();

	}

	@Override
	public void remove(int id) throws Exception {
		// TODO Auto-generated method stub

		PreparedStatement pstmt;
		String sql=	"Delete from  Attachments  where id =? ";
		pstmt = DaoFactory.getDefaultConn().prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.execute();
	}
	public void remove(int id, Connection conn) throws Exception {
		// TODO Auto-generated method stub

		PreparedStatement pstmt;
		String sql=	"Delete from  Attachments  where id =? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.execute();
	}
	public void removeByrefId(int refId, Connection conn) throws Exception {
		PreparedStatement pstmt;
		String sql=	"Delete from  Attachments  where refId =? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, refId);
		pstmt.execute();
	}
	@Override
	public int Add(Attachment entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
