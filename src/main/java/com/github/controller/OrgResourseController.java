package com.github.controller;

import com.github.mapper.OrgResourceMapper;
import com.github.mapper.RoleMapper;
import com.github.model.OrgResource;
import com.github.model.Role;
import com.github.pagehelper.PageHelper;
import com.github.util.DataTableJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("banner")
public class OrgResourseController {

	@Resource private OrgResourceMapper orgResourceMapper;

	@ResponseBody
	@RequestMapping({"", "/", "list"})
	public DataTableJson<OrgResource> list(@RequestParam(required = false, defaultValue = "1") Integer page,
										   @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
										   OrgResource orgResource) {

		PageHelper.startPage(page, pageSize);
		List<OrgResource> orgResourcesList = orgResourceMapper.getList(null);
		for(OrgResource r : orgResourcesList){
			r.setImgUrl("http://teacher.chaoxing.com" + r.getImgUrl());
		}
		DataTableJson<OrgResource> dataTableJson = new DataTableJson<>(orgResourcesList);
		return dataTableJson;
	}

	@GetMapping("add")
	public String add() {
		return "page/banner-add";
	}
	@PostMapping("add")
	public String add(OrgResource orgResource) {
//		role.setCreateTime(new Date());
		orgResource.setResType(1);
//fid先写死
		orgResource.setFid(00001);
//		String imgUrl = orgResource.getImgUrl();
//		orgResource.setImgUrl(imgUrl.substring(imgUrl.indexOf("\"")+1,imgUrl.lastIndexOf("\"")));
		orgResourceMapper.insert(orgResource);
		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
	}

	@ResponseBody
	@GetMapping("{id}/delete")
	public Object delete(@PathVariable Integer id) {
		orgResourceMapper.deleteByPrimaryKey(id);
		return "";
	}

	@GetMapping("{id}/edit")
	public Object edit(@PathVariable Integer id, Model model) {
		OrgResource or = orgResourceMapper.selectByPrimaryKey(id);
		model.addAttribute("banner", orgResourceMapper.selectByPrimaryKey(id));
		return "page/banner-edit";
	}

	@PostMapping("update")
	public String update(OrgResource orgResource) {
//		role.setCreateTime(new Date());
		orgResourceMapper.updateByPrimaryKeySelective(orgResource);
		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
	}

}
