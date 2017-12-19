package com.github.controller;

import com.github.mapper.PermissionMapper;
import com.github.mapper.RoleMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("t")
public class TestController {

	@Resource private RoleMapper roleMapper;
	@Resource private PermissionMapper permissionMapper;

	@ResponseBody
	@GetMapping({"", "/"})
	public Object index() {



		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole("su")) {
			System.err.println("asfsfsfadslfdajf");
		}


		return roleMapper.getList();
//		return roleMapper.getMap();
	}

}
