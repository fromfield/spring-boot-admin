package com.github.controller;

import com.github.mapper.NoticeTypeMapper;
import com.github.model.NoticeType;
import com.github.model.OrganizationExtend;
import com.github.model.User;
import com.github.pagehelper.PageHelper;
import com.github.service.NoticeTypeService;
import com.github.service.OrganizationExtendService;
import com.github.util.Constants;
import com.github.util.CurrentUser;
import com.github.util.DataTableJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("notice/type")
public class NoticeTypeController {
    @Resource
    private NoticeTypeMapper noticeTypeMapper;
    @Resource
    private NoticeTypeService noticeTypeService;
    @Resource
    private OrganizationExtendService organizationExtendService;

    /**
     * 通知类型列表页面
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 15:58
     */
    @GetMapping("list")
    public String typesIndex(@CurrentUser User user,@RequestParam(required = false) Integer fid, Model model) {
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        System.err.println(fid);
        if(fid==null){
            fid = user.getFid();
        }
        if (!Constants.ADMINCODE.equals(rolecode)) { //非管理员
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);
        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);
        System.err.println(orgs);//debug
        model.addAttribute("user", user);
        return "page/notice-type-list";
    }

    /**
     * 获取通知类型列表数据
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:04
     */
    @PostMapping("list")
    @ResponseBody
    public DataTableJson getType(@RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                             NoticeType type) {
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> datalist = noticeTypeMapper.getList(type);
        DataTableJson<Map<String, Object>> dataTableJson = new DataTableJson<Map<String, Object>>(datalist);
        return dataTableJson;
    }

    /**
     * 添加通知类型页面（如果不是弹窗添加用）
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 15:58
     */
    @GetMapping("add")
    public String addIndex(@CurrentUser User user, @RequestParam(required = false) Integer fid, Model model) {
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        if (!Constants.ADMINCODE.equals(rolecode)) {
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);
        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);
        System.err.println(orgs);//debug
        return "page/notice-type-add";
    }

    /**
     * 保存添加通知类型
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:01
     */
    @PostMapping("add")
    public String add(NoticeType noticeType, @CurrentUser User user) {
        System.out.println(noticeType);//debug

        noticeType = noticeTypeService.add(noticeType, user);
        String gourl = "/page/success.html";
        if (noticeType == null) {
            gourl = "/page/fail.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }


    /**
     * 编辑通知类型页面（如果不是弹窗添加用）
     *
     * @param id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:06
     */
    @GetMapping("edit/{id}")
    public String editIndex(@PathVariable Integer id, Model model) {
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        NoticeType noticeType=noticeTypeService.get(id);
        Integer fid=noticeType.getOrgId();//user.getFid();
        if (!Constants.ADMINCODE.equals(rolecode)) {//非管理员只能查询当前的机构
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);
        model.addAttribute("fid", fid);
        model.addAttribute("orgs", orgs);
        model.addAttribute("noticeType",noticeType);

        return "page/notice-type-edit";
    }

    /**
     * 保存编辑通知类型
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:06
     */
    @PostMapping("edit")
    public String edit(NoticeType noticeType, @CurrentUser User user) {
        noticeType = noticeTypeService.update(noticeType, user);
        String gourl = "/page/success.html";
        if (noticeType == null) {
            gourl = "/page/fail.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }

    /**
     * 删除通知类型
     *
     * @param id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:15
     */
    @PostMapping("del/{id}")
    public String del(@PathVariable Integer id) {
        int result = noticeTypeService.del(id);
        String gourl = "/page/fail.html";
        if (result > 0) {
            gourl = "/page/success.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }


    /**
     * 修改通知类型状态
     *
     * @param id     类型id
     * @param status 要修改为的状态0 无效  1有效
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("status/{id}")
    @ResponseBody
    public String changeStatus(@PathVariable Integer id, Integer status) {

        return "";
    }


    /**
     * 根据fid 获取类型 （添加和编辑通知的时候用）
     *
     * @param fid
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:31
     */
    public String getTypesByFid(@PathVariable Integer fid) {
        return "";
    }


}
