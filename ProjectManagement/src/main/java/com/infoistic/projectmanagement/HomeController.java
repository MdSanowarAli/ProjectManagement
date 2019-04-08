package com.infoistic.projectmanagement;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infoistic.dao.IUserDao;
import com.infoistic.domain.ProjectAssign;
import com.infoistic.domain.User;

@Controller
public class HomeController {
	@Autowired
	IUserDao userDao;

	@RequestMapping("/")
	public String index() {

		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {

		return "login";
	}
	
/*	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout() {

		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(Model model, @ModelAttribute User user, HttpServletRequest request) {
		User dbuser;
		try {
			dbuser = userDao.login(user.getUserId(), user.getPassword());

			if (dbuser != null) {
				HttpSession session= request.getSession();
				session.setAttribute("Name", dbuser.getFirstName());
				session.setAttribute("ID", dbuser.getId());
				return "layout";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "login";
	}*/
	
	@RequestMapping(value = "/forgatepassword", method = RequestMethod.GET)
	public String forgotpasswordGet(Model model) {
		try {
			User user = new User();
			model.addAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/forgatepassword";
	}

	
	@RequestMapping(value = "/forgatepassword", method = RequestMethod.POST)
	public String forgotpassword(Model model, @ModelAttribute User user, HttpServletRequest request) {
		try {
			user.setSecurityCode(new Timestamp(System.currentTimeMillis()).getTime() + "");
			userDao.passwordResetRequest(user);
			String resultPath = request.getHeader("Host") + request.getContextPath();
			String email = java.net.URLEncoder.encode(user.getEmailId(), "ISO-8859-1");
			String body = "<h1>...####...</h1>" + "\n<a href='http://" + resultPath + "/resetpassword?sq="
					+ user.getSecurityCode() + "&em=" + email + "'>Reset Password</a>";
			MailSender.sendMail(user.getEmailId(), "Reset password", body);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			return "/forgatepassword";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	public String resetpasswordGet(Model model, @RequestParam(required = false, defaultValue = "0") String sq,
			@RequestParam(required = false, defaultValue = "0") String em) {
		try {
			User user = new User();
			user.setSecurityCode(sq);
			String email = java.net.URLDecoder.decode(em, "ISO-8859-1");
			user.setEmailId(email);
			model.addAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/resetpassword";
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public String resetpassword(Model model, @ModelAttribute User user, HttpServletRequest request) {
		try {
			userDao.ResetpasswordUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
}
