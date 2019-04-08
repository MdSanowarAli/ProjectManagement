package com.infoistic.dao;

import java.util.List;

import com.infoistic.domain.Attachment;
import com.infoistic.domain.Project;

public interface IProjectDao  extends IRepository<Project>{
	
	void AddwithFile(Project entity) throws Exception;
	List<Attachment> UpdatewithFile(Project entity,List<Attachment> attachmentsNew) throws Exception;
	List<Project> get(String searchString) throws Exception ;

}
