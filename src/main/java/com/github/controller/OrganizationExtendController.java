package com.github.controller;

import com.github.mapper.OrgMenuMapper;
import com.github.mapper.OrganizationExtendMapper;
import com.github.model.OrgMenu;
import com.github.model.OrganizationExtend;
import com.github.model.User;
import com.github.pagehelper.PageHelper;
import com.github.service.RoleService;
import com.github.util.Constants;
import com.github.util.CurrentUser;
import com.github.util.DataTableJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * @author fromfield
 * @create 2017-11-06 17:03
 **/

@Controller
@RequestMapping("org")
public class OrganizationExtendController {

    @Resource
    private OrganizationExtendMapper organizationExtendMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private OrgMenuMapper orgMenuMapper;

    @ResponseBody
    @RequestMapping("orgs")
    public DataTableJson<OrganizationExtend> orgList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                     OrganizationExtend organizationExtend){
        PageHelper.startPage(page, pageSize);
        return new DataTableJson<>(organizationExtendMapper.getList(organizationExtend));
    }

    @GetMapping("add")
    public String add() {
        return "page/org-add";
    }

    @PostMapping("add")
    public String add(OrganizationExtend organizationExtend){
        //添加前需要判断机构id是否存在、域名是否存在
        organizationExtend.setCreateTime(new Date());
        organizationExtendMapper.insertSelective(organizationExtend);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @GetMapping("{id}/edit")
    public Object edit(@PathVariable Integer id, Model model) {
        model.addAttribute("org", organizationExtendMapper.selectByPrimaryKey(id));
        return "page/org-edit";
    }

    @RequestMapping(value = "{org_id}/delete", method = RequestMethod.GET)
    public String deleteById(@PathVariable("org_id") Integer id){
        organizationExtendMapper.deleteByPrimaryKey(id);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 禁用、启用二级机构
     * @param id
     * @return
     */
    @RequestMapping(value = "{orgId}/{status}/update_status", method = RequestMethod.GET)
    public String updateStatus(@PathVariable("orgId") Integer id, @PathVariable("status") Integer status){
        OrganizationExtend o = new OrganizationExtend();
        o.setId(id);
        o.setStatus(status);
        organizationExtendMapper.updateByPrimaryKeySelective(o);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @PostMapping("update")
    public String update(OrganizationExtend organizationExtend){
        //修改前需要判断机构id是否存在、域名是否存在
        organizationExtend.setCreateTime(new Date());
        organizationExtendMapper.updateByPrimaryKey(organizationExtend);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @ResponseBody
    @RequestMapping("user-org")
    public Object getOrgsByUser(@CurrentUser User loginUser){
        if(loginUser == null){
            return new ArrayList<OrganizationExtend>();
        }
        Set<String> roles = roleService.getUserRoleStringSet(loginUser.getId());
        if(!roles.contains(Constants.ADMINCODE)){
            return new ArrayList<OrganizationExtend>();
        }
        return organizationExtendMapper.getList(new OrganizationExtend());
    }




    @ResponseBody
    @RequestMapping("{fid}/get-org-menus")
    public DataTableJson<OrgMenu> orgMenus(@RequestParam(required = false, defaultValue = "1") Integer page,
                           @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                           OrgMenu orgMenu, @PathVariable("fid") Integer fid, Integer terType){
        PageHelper.startPage(page, pageSize);
        orgMenu.setFid(fid);
        orgMenu.setTerType(terType);
        return new DataTableJson<>(orgMenuMapper.getList(orgMenu));
    }

    @RequestMapping("{fid}/org-menus")
    public String openOrgMeun(@PathVariable("fid") Integer fid, Model model){
        model.addAttribute("fid", fid);
        return"page/org-menus";
    }
    @RequestMapping("{fid}/org-menu-pc")
    public String openOrgMeunPc(@PathVariable("fid") Integer fid, Model model){
        model.addAttribute("fid", fid);
        return"page/org-menu";
    }
    @RequestMapping("{fid}/org-menu-phone")
    public String openOrgMeunPhone(@PathVariable("fid") Integer fid, Model model){
        model.addAttribute("fid", fid);
        return"page/org-menu-phone";
    }


    @GetMapping("{fid}/add-menu")
    public String addMenu(@PathVariable("fid") Integer fid, Model model, Integer terType){
        model.addAttribute("fid", fid);
        model.addAttribute("terType", terType);
        return "page/menu-add";
    }

    @PostMapping("add-menu")
    public String addMenu(OrgMenu orgMenu){
        orgMenuMapper.insertSelective(orgMenu);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @RequestMapping(value = "{menuId}/delete-menu", method = RequestMethod.GET)
    public String deleteMenu(@PathVariable("menuId") Integer menuId){
        orgMenuMapper.deleteByPrimaryKey(menuId);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @RequestMapping("{id}/edit-menu")
    public Object editMenu(@PathVariable Integer id, Model model) {
        model.addAttribute("menu", orgMenuMapper.selectByPrimaryKey(id));
        return "page/menu-edit";
    }

    @PostMapping("update-menu")
    public String updateMenu(OrgMenu orgMenu) {
        orgMenuMapper.updateByPrimaryKeySelective(orgMenu);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }
}
