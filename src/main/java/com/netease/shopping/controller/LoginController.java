package com.netease.shopping.controller;

import com.netease.shopping.model.LoginTicket;
import com.netease.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller public class LoginController {

	@Autowired UserService userService;

	@RequestMapping(path = { "/reglogin" }, method = { RequestMethod.GET }) public String reglogin() {
		return "login";
	}

	@RequestMapping(path = { "/login" }, method = { RequestMethod.POST }) public String login(Model model,
			@RequestParam("userName") String userName, @RequestParam("password") String password, HttpServletResponse response) {
		try {
			Map<String, Object> map = userService.login(userName, password);
			if (map.containsKey("ticket")) {
				Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				response.addCookie(cookie);
				return "redirect:/";
			} else {
				model.addAttribute("msg", map.get("msg"));
				return "login";
			}
		} catch (Exception e) {

			e.printStackTrace();
			return "login";
		}
	}

	@RequestMapping(path = { "/logout" }, method = { RequestMethod.GET, RequestMethod.POST }) public String logout(
			@CookieValue("ticket") String ticket) {
		userService.logout(ticket);
		return "redirect:/";
	}

	@RequestMapping(path = { "/redirect/{code}" }, method = { RequestMethod.GET }) public RedirectView redirect(
			@PathVariable("code") int code, HttpSession session) {
		session.setAttribute("msg", "jump from redirect");
		RedirectView red = new RedirectView("/", true);
		if (code == 301) {
			red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		}
		return red;
	}

}
