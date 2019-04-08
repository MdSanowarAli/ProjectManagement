package com.infoistic.projectmanagement;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infoistic.dao.IUserDao;
import com.infoistic.domain.AuthUser;
import com.infoistic.domain.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	IUserDao userDao;
	
	@RequestMapping("")
	public String index(Model model) {
		try {
			model.addAttribute("list", userDao.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/index";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String add(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		String returnPath = "";
		try {
			User user;
			if (Id > 0) {
				user = userDao.getById(Id);
				returnPath = "user/update";
			} else {
				user = new User();
				returnPath = "user/create";
			}
			model.addAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnPath;
	}

	@RequestMapping(value = "/saveContact", method = RequestMethod.POST)
	public String saveContact(Model model, @ModelAttribute User user) {
		String returnPath = "";
		try {

			if (user.getId() > 0) {
				returnPath = "user/update";

				userDao.Update(user);
			} else {
				returnPath = "user/create";

				userDao.Add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			model.addAttribute("message", e.getMessage());
			return returnPath;
		}
		return "redirect:/user?message";
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			model.addAttribute("user", userDao.getById(Id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/details";
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public WsResponse delete(@RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			userDao.remove(Id);
			return WsResponse.createSuccessResponse("Data delete succes", "");
		} catch (Exception e) {
			e.printStackTrace();
			return WsResponse.createErrorResponse("505", e.getMessage(), null);
		}
	}
	
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String changepassword(Model model) {
		//AppUser user = new AppUser();
		User user = new User();
		model.addAttribute("user", user);
		return "/user/changepassword";
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String changepasswordP(Model model, @ModelAttribute User user, HttpServletRequest request) {
		try {
			AuthUser userr = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//User userr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user.setId(userr.getUserId());
			userDao.PasswordUpdate(user);
			return "/logout";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			return "/user/changepassword";
		}
	}

}
