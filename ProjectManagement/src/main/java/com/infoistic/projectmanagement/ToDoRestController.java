package com.infoistic.projectmanagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;
import com.infoistic.dao.IToDoDao;


@Scope("request")
@RestController
public class ToDoRestController {
	@Autowired
	IToDoDao toDoDao;

//for Search
/*@RequestMapping(value = "/api/todo", method = RequestMethod.GET) // getChartData
	public List<ProjectAssign> getPatient(HttpServletRequest request) {
		try {
			String query = request.getParameter("query").trim();
			return  toDoDao.get(query);
		} catch (Exception ex) {
			return null;
		}
	}*/


}

