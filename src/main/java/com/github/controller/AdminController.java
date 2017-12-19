package com.github.controller;

import com.github.service.UserService;
import com.github.shiro.SsoToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class AdminController {

	@Resource private UserService userService;

	@GetMapping({"", "/", "login"})
	public String login() {

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/index";
		}

		return "login";
	}

	@PostMapping({"", "/", "login"})
	public String login(HttpServletRequest request, Model model) {

		// 认证未通过或反复POST认证, 进入这里
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/index";
		}

		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");

		String errorMessage = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			errorMessage = "用户名或密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			errorMessage = "用户名或密码错误";
		} else if (AuthenticationException.class.getName().equals(exceptionClassName)) {
			errorMessage = "用户名或密码错误";
		} else if (exceptionClassName != null) {
			errorMessage = "其他错误：" + exceptionClassName;
		}

		model.addAttribute("errorMessage", errorMessage);
		return "login";
	}

	@GetMapping("login/sso")
	public String sso(@CookieValue(required = false) Integer fid, @CookieValue(name = "UID", required = false) Integer uid, HttpServletRequest request) {

		String domain = request.getScheme() + "://" + request.getServerName() + "/";
		if (fid == null || uid == null) {
			return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "http://passport2.chaoxing.com/wlogin?refer=" + domain + "/login/sso/callback";
		}

		return UrlBasedViewResolver.FORWARD_URL_PREFIX + "/login/sso/callback";
	}
	@GetMapping("login/sso/callback")
	public String ssoCallback(@CookieValue Integer fid, @CookieValue("UID") Integer uid) {

		SsoToken token = new SsoToken(fid, uid);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			return UrlBasedViewResolver.FORWARD_URL_PREFIX + "/assets/401.html";
		}

		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/index";
	}


	@GetMapping("logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setValue(null);
			cookie.setDomain("chaoxing.com");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/login";
	}

	@GetMapping("index")
	public String index(Model model) {

		model.addAttribute("userMenuPermissionList", userService.getUserMenuPermissionList(1));
		return "index";
	}

}
