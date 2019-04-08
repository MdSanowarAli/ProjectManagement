package com.infoistic.dao;

import java.util.Date;
import java.util.List;

import com.infoistic.domain.ProjectAssign;
import com.infoistic.domain.ToDo;

public interface IToDoDao extends IRepository<ProjectAssign> {
  List<ProjectAssign> getByUserId( int userId) throws Exception ;
  
  //List<ProjectAssign> get(String searchString) throws Exception ;   //for Search
  
  List<ProjectAssign> getSearch(Date estimatedEndDate, String status)
			throws Exception ;
}
