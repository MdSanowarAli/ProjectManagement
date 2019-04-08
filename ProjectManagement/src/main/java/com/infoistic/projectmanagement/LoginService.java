package com.infoistic.projectmanagement;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infoistic.dao.IUserDao;
import com.infoistic.domain.AuthUser;
import com.infoistic.domain.User;



@Service
public class LoginService  implements UserDetailsService{

	@Autowired
	private IUserDao userDao;

@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	AuthUser user=null;
		try {
			user =userDao.login(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return (UserDetails) user;
		//return new org.springframework.security.core.userdetails.User(usr.getEmailId(), usr.getEmailId(), getAuthority("ClinicId_"+usr.getClinicId()));
	}

/*@RequestMapping(value = "/login", method = RequestMethod.POST)
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
	
}
