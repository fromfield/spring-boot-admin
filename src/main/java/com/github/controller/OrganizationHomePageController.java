package com.github.controller;

import com.github.mapper.OrgResourceMapper;
import com.github.mapper.OrganizationExtendMapper;
import com.github.model.OrgResource;
import com.github.model.OrganizationExtend;
import com.github.model.Role;
import com.github.model.User;
import com.github.pagehelper.PageHelper;
import com.github.util.CurrentUser;
import com.github.util.DataTableJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author fromfield
 * @create 2017-11-06 17:03
 **/

@Controller
@RequestMapping("orgIndex")
public class OrganizationHomePageController {

    @Resource
    private OrganizationExtendMapper organizationExtendMapper;
    @Resource
    private OrgResourceMapper orgResourceMapper;

    @ResponseBody
    @RequestMapping("orgs")
    public DataTableJson<OrganizationExtend> orgList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                     OrganizationExtend organizationExtend,@CurrentUser User user){
        PageHelper.startPage(page, pageSize);
        List<OrganizationExtend> orgList =organizationExtendMapper.getList(organizationExtend);
        for(int i=0;i<orgList.size();i++){
            int fid = orgList.get(i).getFid();//获取fid,将logo地址暂时放在domain字段。
            orgList.get(i).setDomain("http://teacher.chaoxing.com/resources/upload/logo/"+fid+".png");//到教发系统取图片展示。
        }
        return new DataTableJson<>(orgList);
    }

    @GetMapping("{fid}/banners")
    public Object banners(@PathVariable Integer fid, Model model) {
        model.addAttribute("fid", fid);
        return "page/org-banners";
    }

    @GetMapping("{fid}/editBanner")
    public Object bannerForOrg(@PathVariable Integer fid, Model model) {
        OrgResource o = new OrgResource();
        o.setFid(fid);
        o.setTerType(1);

        List<OrgResource> orgResourcesList = orgResourceMapper.getList(o);
        for(OrgResource r : orgResourcesList){
            r.setImgUrl("http://teacher.chaoxing.com" + r.getImgUrl());
        }
        model.addAttribute("banners", orgResourcesList);
        model.addAttribute("fid", fid);
        return "page/orgBanner-list";
    }

    @GetMapping("{fid}/editBanner-phone")
    public Object bannerForOrgPhone(@PathVariable Integer fid, Model model) {
        OrgResource o = new OrgResource();
        o.setFid(fid);
        o.setTerType(0);

        List<OrgResource> orgResourcesList = orgResourceMapper.getList(o);
        for(OrgResource r : orgResourcesList){
            r.setImgUrl("http://teacher.chaoxing.com" + r.getImgUrl());
        }
        model.addAttribute("banners", orgResourcesList);
        model.addAttribute("fid", fid);
        return "page/orgBanner-list-phone";
    }

    @ResponseBody
    @GetMapping("{id}/deleteBanner")
    public Object delete(@PathVariable Integer id) {
        orgResourceMapper.deleteByPrimaryKey(id);
        return "";
    }

    @GetMapping("addBanner")
    public String add() {
        return "page/banner-add";
    }
    @PostMapping("addBanner")
    public String add(OrgResource orgResource,@CurrentUser User user) {
        orgResource.setResType(1);
        orgResource.setImgUrl("/resources/upload" + orgResource.getImgUrl());
        orgResourceMapper.insert(orgResource);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @GetMapping("{fid}/goToaddBannerPage")
    public String goToaddBannerPage(Model model,@PathVariable String fid, Integer terType) {
        model.addAttribute("terType", terType);
        model.addAttribute("fid", fid);
        return "page/banner-add.html";
    }

    @GetMapping("{id}/edit")
    public Object edit(@PathVariable Integer id, Model model) {
        model.addAttribute("banner", orgResourceMapper.selectByPrimaryKey(id));
        return "page/banner-edit";
    }

    @PostMapping("updateBanner")
    public String update(OrgResource orgResource) {
        if(!orgResource.getImgUrl().contains("/resources/upload")){
            orgResource.setImgUrl("/resources/upload" + orgResource.getImgUrl());
        }
        orgResourceMapper.updateByPrimaryKeySelective(orgResource);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

     //编辑logo
    @GetMapping("{fid}/editLogo")
    public Object changeLogo(@PathVariable Integer fid, Model model) {
        model.addAttribute("fid",fid);
        return "page/logo-edit";
    }

    //修改logo成功后，重新加载机构首页
    @PostMapping("reloadHomeOfOrganizations")
    public Object reloadHomeOfOrganizations(Model model) {
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }


}
