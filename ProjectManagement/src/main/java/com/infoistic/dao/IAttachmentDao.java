package com.infoistic.dao;

import java.sql.Connection;

import com.infoistic.domain.Attachment;

public interface IAttachmentDao extends IRepository<Attachment> {
	int  AddFromAnother(Attachment entity , Connection conn) throws Exception;
}
