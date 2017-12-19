package com.github.controller;

import com.github.mapper.NoticeMapper;
import com.github.mapper.NoticeTypeMapper;
import com.github.model.Notice;
import com.github.model.NoticeType;
import com.github.model.OrganizationExtend;
import com.github.model.User;
import com.github.model.extend.NoticeExtend;
import com.github.pagehelper.PageHelper;
import com.github.service.NoticeService;
import com.github.service.NoticeTypeService;
import com.github.service.OrganizationExtendService;
import com.github.util.Constants;
import com.github.util.CurrentUser;
import com.github.util.DataTableJson;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("notice")
public class NoticeController {

    @Resource
    NoticeMapper noticeMapper;
    @Resource
    OrganizationExtendService organizationExtendService;
    @Resource
    NoticeService noticeService;
    @Resource
    NoticeTypeService noticeTypeService;
    @Resource
    NoticeTypeMapper noticeTypeMapper;

    /**
     * 通知列表页面
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 15:58
     */
    @GetMapping("list")
    public String Index(@CurrentUser User user, Model model) {
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug

        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
//        if (subject.hasRole(Constants.ADMINCODE)) {
//            System.err.println("asfsfsfadslfdajf");
//        }
        Integer fid = user.getId();
        if (!Constants.ADMINCODE.equals(rolecode)) { //非管理员
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);
        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);
        System.err.println(orgs);//debug
        model.addAttribute("user", user);
        return "page/notice-list";
    }

    /**
     * 获取通知列表数据
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:04
     */
    @PostMapping("list")
    @ResponseBody
    public DataTableJson getList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                 NoticeExtend noticeExtend, @CurrentUser User user) {
        PageHelper.startPage(page, pageSize);
//        int fid=34797;//user.getFid();
//        noticeExtend.setFid(fid);
        List<NoticeExtend> datalist = noticeMapper.getList(noticeExtend);
        DataTableJson<NoticeExtend> dataTableJson = new DataTableJson<NoticeExtend>(datalist);
        return dataTableJson;
    }

    /**
     * 添加通知页面
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 15:58
     */
    @GetMapping("add")
    public String addIndex(@CurrentUser User user, @RequestParam(required = false) Integer fid, Model model) {
        //1.获取机构列表 （管理员）
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        fid = fid == null ? user.getFid() : fid;
        if (!Constants.ADMINCODE.equals(rolecode)) {
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);

        //2.获取某机构对应的类型列表
        NoticeType noticeType = new NoticeType();
        noticeType.setOrgId(fid);
        noticeType.setStatus("1");//需要修改为常量
        List<Map<String, Object>> types = noticeTypeMapper.getList(noticeType);

        model.addAttribute("types", types);
        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);

        System.err.println(orgs);//debug
        System.err.println(types);//debug

        return "page/notice-add";
    }

    /**
     * 保存添加通知
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:01
     */
    @PostMapping("add")
    public String add(@CurrentUser User user, Notice notice) {
        System.out.println(notice);//debug
        notice = noticeService.add(notice, user);
        String gourl = "/page/success.html";
        if (notice == null) {
            gourl = "/page/fail.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }


    /**
     * 编辑通知页面
     *
     * @param id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:06
     */
    @GetMapping("edit/{id}")
    public String editIndex(@PathVariable Integer id, Model model) {
        //1.获取要编辑的通知信息
        Notice notice = noticeService.get(id);

        //2.获取某机构对应的类型列表
        int typeid = notice.getTypeId();
        NoticeType noticeType = noticeTypeService.get(typeid);
        NoticeType tempNoticeType = new NoticeType();
        if(noticeType!=null){
            tempNoticeType.setOrgId(noticeType.getOrgId());
        }
        List<Map<String, Object>> types = noticeTypeMapper.getList(tempNoticeType);

        //3.获取机构列表 （管理员）
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        Integer fid = tempNoticeType.getOrgId();//user.getFid();
        if (!Constants.ADMINCODE.equals(rolecode)) {//非管理员只能查询当前的机构
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);

        model.addAttribute("notice", notice);
        model.addAttribute("types", types);
        model.addAttribute("fid", fid);
        model.addAttribute("orgs", orgs);

        return "page/notice-edit";
    }

    /**
     * 保存编辑通知
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:06
     */
    @PostMapping("edit")
    public String edit(Notice notice, @CurrentUser User user) {
        System.out.println(notice);//debug
        notice = noticeService.update(notice, user);
        String gourl = "/page/success.html";
        if (notice == null) {
            gourl = "/page/fail.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }


    /**
     * 删除通知
     *
     * @param id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:15
     */
    @PostMapping("del/{id}")
    public String del(@PathVariable Integer id) {
        int result = noticeService.del(id);

        String gourl = "/page/fail.html";
        if (result > 0) {
            gourl = "/page/success.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }

    /**
     * 修改通知状态
     *
     * @param id     通知id
     * @param status 要修改为的状态0 无效  1有效
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("status/{id}/{status}")
    public String changeStatus(@PathVariable Integer id,@PathVariable Integer status,@CurrentUser User user) {
        Notice notice=noticeService.get(id);
        String gourl = "/page/success.html";
        if(notice!=null){
            notice.setStatus(status.toString());
            notice = noticeService.update(notice, user);
            if (notice == null) {
                gourl = "/page/fail.html";
            }
        }else{
            gourl = "/page/fail.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }


    /**
     * 首页显示的通知列表页面
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @GetMapping("IndexShow")
    public String IndexShow() {
        return "";
    }

    /**
     * 获取首页显示的通知列表页面数据
     *
     * @param fid 机构id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("IndexShow")
    public String IndexShowData(Integer fid) {
        return "";
    }

    /**
     * 将通知添加到首页显示
     *
     * @param id 通知id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("showIndex/{id}")
    @ResponseBody
    public String addIndex(@PathVariable Integer id) {
        return "";
    }

    /**
     * 将通知移除首页显示
     *
     * @param id 通知id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("hideIndex/{id}")
    @ResponseBody
    public String delIndex(@PathVariable Integer id) {
        return "";
    }


}
