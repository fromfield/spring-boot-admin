package com.github.controller;

import com.github.mapper.UserMapper;
import com.github.model.User;
import com.github.pagehelper.PageHelper;
import com.github.util.DataTableJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

	@Resource private UserMapper userMapper;

	@ResponseBody
	@RequestMapping({"", "/", "list"})
	public DataTableJson<Map<String, Object>> list(@RequestParam(required = false, defaultValue = "1") Integer page,
	                                               @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
	                                               User user) {

		PageHelper.startPage(page, pageSize);
		List<Map<String, Object>> userList = userMapper.getManageList();
		DataTableJson<Map<String, Object>> dataTableJson = new DataTableJson<Map<String, Object>>(userList);

		return dataTableJson;
	}

	@GetMapping("add")
	public String add() {
		return "page/user-add";
	}
	@PostMapping("add")
	public String add(User user) {
		user.setCreateTime(new Date());
		userMapper.add(user);
		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
	}

	@ResponseBody
	@GetMapping("{id}/delete")
	public Object delete(@PathVariable Integer id) {
		userMapper.delete(id);
		return "";
	}

	@GetMapping("{id}/edit")
	public Object edit(@PathVariable Integer id, Model model) {
		model.addAttribute("user", userMapper.get(id));
		return "page/user-edit";
	}

	@PostMapping("update")
	public String update(User user) {
		user.setCreateTime(new Date());
		userMapper.update(user);
		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
	}

}
