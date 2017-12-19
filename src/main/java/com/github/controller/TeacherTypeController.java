package com.github.controller;

import com.github.mapper.FamousTeacherMapper;
import com.github.mapper.FamousTeacherTypeMapper;
import com.github.model.FamousTeacher;
import com.github.model.FamousTeacherType;
import com.github.model.OrganizationExtend;
import com.github.model.User;
import com.github.pagehelper.PageHelper;
import com.github.service.FamousTeacherTypeService;
import com.github.service.OrganizationExtendService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("teacher/type")
public class TeacherTypeController {
    @Resource
    FamousTeacherTypeMapper famousTeacherTypeMapper;
    @Resource
    FamousTeacherTypeService famousTeacherTypeService;
    @Resource
    private OrganizationExtendService organizationExtendService;

    /**
     * 名师类型列表页面
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:34
     */
    @GetMapping({"", "/", "list"})
    public String typesIndex(@CurrentUser User user,@RequestParam(required = false) Integer fid,@RequestParam(required = false,defaultValue = "0") Integer pid,Model model) {
        //如果pid不为0 则为子标签管理
        FamousTeacherType parentType=null;
        if(pid!=0){
            parentType=famousTeacherTypeService.get(pid);
        }
        fid=fid==null?(parentType==null?(user==null?fid:user.getFid()):parentType.getFid()):fid;

        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        if (!Constants.ADMINCODE.equals(rolecode)||parentType!=null) {
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);
        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);
        model.addAttribute("pid", pid);
        return "page/teacher-type-list";
    }

    /**
     * 获取名师类型列表数据
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:35
     */
    @PostMapping("list")
    @ResponseBody
    public DataTableJson<FamousTeacherType> list(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                 FamousTeacherType object, @CurrentUser User user) {

        PageHelper.startPage(page, pageSize);
        if(object.getFid()==null){
            object.setFid(user.getFid());
        }
        if(object.getPid()==null){
            object.setPid(0);
        }
        List<FamousTeacherType> roleList = famousTeacherTypeMapper.getList(object);
        DataTableJson<FamousTeacherType> dataTableJson = new DataTableJson<>(roleList);
        return dataTableJson;
    }

    /**
     * 添加名师类型页面（如果不是弹窗添加用）
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:35
     */
    @GetMapping("add/{pid}/{fid}")
    public String addIndex(@PathVariable Integer pid,@PathVariable Integer fid,@CurrentUser User user,Model model) {
        //1.如果pid!=0 ,获取类型信息
        FamousTeacherType parentType=null;
        List<FamousTeacherType> parentTypes=new ArrayList<FamousTeacherType>();

        if(pid!=null&&pid!=0){
            parentType=famousTeacherTypeService.get(pid);
            FamousTeacherType tempparentType=new FamousTeacherType();
            tempparentType.setPid(parentType.getPid());
            tempparentType.setFid(parentType.getFid());
            parentTypes=famousTeacherTypeMapper.getList(tempparentType);
        }
        fid=fid==null?(parentType==null?(user==null?fid:user.getFid()):parentType.getFid()):fid;
        //2.如果是超级管理员 则获取机构列表
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        if (!Constants.ADMINCODE.equals(rolecode)||parentType!=null) {
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);

        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);
        model.addAttribute("parentTypes", parentTypes);
        model.addAttribute("pid", pid);
        return "page/teacher-type-add";
    }


    /**
     * 保存添加名师类型
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:01
     */
    @PostMapping("add")
    public String add(FamousTeacherType object) {
//        object.setCreateTime(new Date());
        famousTeacherTypeMapper.insert(object);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }


    /**
     * 编辑名师类型页面（如果不是弹窗添加用）
     *
     * @param id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:06
     */
    @GetMapping("edit/{id}")
    public Object edit(@PathVariable Integer id, Model model) {

        //1.获取当前信息
        FamousTeacherType curtype=famousTeacherTypeMapper.selectByPrimaryKey(id);
        Integer fid=curtype.getFid();
        Integer pid=curtype.getPid();
        //2.如果pid!=0 ,获取类型信息
        FamousTeacherType parentType=new FamousTeacherType();
        List<FamousTeacherType> parentTypes=new ArrayList<FamousTeacherType>();
        if(pid!=null&&pid!=0){
            parentType=famousTeacherTypeService.get(pid);
            FamousTeacherType tempparentType=new FamousTeacherType();
            tempparentType.setPid(parentType.getPid());
            tempparentType.setFid(parentType.getFid());
            parentTypes=famousTeacherTypeMapper.getList(tempparentType);
        }


        //2.如果是超级管理员 则获取机构列表
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        if (!Constants.ADMINCODE.equals(rolecode) || pid!=0) {
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);




        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);
        model.addAttribute("pid", pid);
        model.addAttribute("parentTypes", parentTypes);
        model.addAttribute("object",curtype);
        return "page/teacher-type-edit";
    }

    /**
     * 保存编辑通知类型
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:06
     */
    @PostMapping("edit")
    public String update(FamousTeacherType object) {
//        object.setCreateTime(new Date());
        famousTeacherTypeService.update(object);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 删除名师类型
     *
     * @param id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:15
     */
    @PostMapping("del/{id}")
    public Object delete(@PathVariable Integer id) {
        int result= famousTeacherTypeService.del(id);
        String gourl = "/page/fail.html";
        if (result > 0) {
            gourl = "/page/success.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }


    /**
     * 修改名师类型状态
     *
     * @param id     类型id
     * @param status 要修改为的状态0 无效  1有效
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("status/{id}/{status}")
    @ResponseBody
    public String changeStatus(@PathVariable Integer id, @PathVariable Integer status) {
        FamousTeacherType famousTeacherType=famousTeacherTypeService.get(id);
        String sstatus=(status==null||status==0)?"0":"1";
        famousTeacherType.setStatus(sstatus);
        famousTeacherTypeService.update(famousTeacherType);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }


    /**
     * 根据fid 获取类型 （添加和编辑通知的时候用）
     *
     * @param fid
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:31
     */
    @RequestMapping("tree/{fid}")
    @ResponseBody
    public List<Map<String,Object>> getTypesByFid(@PathVariable Integer fid) {
      List<Map<String,Object>> list=famousTeacherTypeService.getTree(fid);
        return list;
    }
}
