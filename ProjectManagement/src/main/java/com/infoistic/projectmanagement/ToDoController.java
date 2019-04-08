package com.infoistic.projectmanagement;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infoistic.dao.IProjectDao;
import com.infoistic.dao.IToDoDao;
import com.infoistic.dao.IUserDao;
import com.infoistic.daoImpl.ProjectAssignDao;
import com.infoistic.domain.AuthUser;
import com.infoistic.domain.Project;
import com.infoistic.domain.ProjectAssign;
import com.infoistic.domain.User;




@Controller
@RequestMapping("/todo")
public class ToDoController {

	@Autowired
	IToDoDao toDoDao;
	
	@Autowired
	IUserDao userDao;
	
	@Autowired
	IProjectDao projectDao;
	
	@RequestMapping("")
	public String index(Model model, HttpServletRequest request) {
		try {
			//HttpSession session= request.getSession();
			//int userId=(int) session.getAttribute("ID");
			AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int userId=(int) user.getUserId();
			model.addAttribute("list", toDoDao.getByUserId(userId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "todo/index";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			model.addAttribute("todo", toDoDao.getById(Id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "todo/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @ModelAttribute ProjectAssign projectAssign) {
		String returnPath = "";
		try {

			if (projectAssign.getId() > 0) {
				returnPath = "todo/update";

				toDoDao.Update(projectAssign);
			} else {
				returnPath = "todo/index";

				toDoDao.Add(projectAssign);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("todo", projectAssign);
			model.addAttribute("message", e.getMessage());
			return returnPath;
		}
		return "redirect:/todo?message";
	}
	
/*	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String add(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		String returnPath = "";
		try {
			List<User> user = userDao.get();
			model.addAttribute("user", user);
			List<Project> project = projectDao.get();
			model.addAttribute("project", project);
			ProjectAssign projectAssign;
			if (Id > 0) {
				projectAssign = toDoDao.getById(Id);
				returnPath = "todo/create";
			} else {
				projectAssign = new ProjectAssign();
				returnPath = "todo/create";
			}
			model.addAttribute("todo", projectAssign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnPath;
	}

	@RequestMapping(value = "/saveContact", method = RequestMethod.POST)
	public String saveContact(Model model, @ModelAttribute ProjectAssign projectAssign) {
		String returnPath = "";
		try {

			if (projectAssign.getId() > 0) {
				returnPath = "todo/update";

				toDoDao.Update(projectAssign);
			} else {
				returnPath = "todo/create";

				toDoDao.Add(projectAssign);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("todo", projectAssign);
			model.addAttribute("message", e.getMessage());
			return returnPath;
		}
		return "redirect:/todo?message";
	}*/
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			model.addAttribute("todo", toDoDao.getById(Id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "todo/details";
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public WsResponse delete(@RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			toDoDao.remove(Id);
			return WsResponse.createSuccessResponse("Data delete succes", "");
		} catch (Exception e) {
			e.printStackTrace();
			return WsResponse.createErrorResponse("505", e.getMessage(), null);
		}
	}
}
