package com.infoistic.projectmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import com.infoistic.dao.IProjectAssignDao;
import com.infoistic.dao.IProjectDao;
import com.infoistic.dao.IUserDao;
import com.infoistic.domain.Project;
import com.infoistic.domain.ProjectAssign;
import com.infoistic.domain.User;

@Controller
@RequestMapping("/projectassign")
public class ProjectAssignController {

	@Autowired
	IProjectAssignDao projectAssignDao;
	
	@Autowired
	IUserDao userDao;
	
	@Autowired
	IProjectDao projectDao;
	
	@RequestMapping("")
	public String index(Model model) {
		try {
			model.addAttribute("list", projectAssignDao.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "projectassign/index";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET )
	public String add(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		String returnPath = "";
		try {
			List<User> user = userDao.get();
			model.addAttribute("user", user);
			List<Project> project = projectDao.get();
			model.addAttribute("project", project);
			ProjectAssign projectAssign;
			if (Id > 0) {
				projectAssign = projectAssignDao.getById(Id);
				returnPath = "projectassign/update";
			} else {
				projectAssign = new ProjectAssign();
				returnPath = "projectassign/create";
			}
			model.addAttribute("projectassign", projectAssign);
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
				returnPath = "projectassign/update";

				projectAssignDao.Update(projectAssign);
			} else {
				returnPath = "projectassign/create";

				projectAssignDao.Add(projectAssign);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("projectassign", projectAssign);
			model.addAttribute("message", e.getMessage());
			return returnPath;
		}
		return "redirect:/projectassign?message";
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			model.addAttribute("projectassign", projectAssignDao.getById(Id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "projectassign/details";
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public WsResponse delete(@RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			projectAssignDao.remove(Id);
			return WsResponse.createSuccessResponse("Data delete succes", "");
		} catch (Exception e) {
			e.printStackTrace();
			return WsResponse.createErrorResponse("505", e.getMessage(), null);
		}
	}


}
