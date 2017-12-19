package com.github.controller;

import com.alibaba.fastjson.JSON;
import com.github.mapper.FamousTeacherMapper;
import com.github.mapper.RecommendedFamousTeacherMapper;
import com.github.model.*;
import com.github.pagehelper.PageHelper;
import com.github.service.FamousTeacherService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Resource
    FamousTeacherMapper famousTeacherMapper;
    @Resource
    FamousTeacherService famousTeacherService;
    @Resource
    private OrganizationExtendService organizationExtendService;

    @Resource
    private RecommendedFamousTeacherMapper recommendedFamousTeacherMapper;

    /**
     * 列表页面
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 15:58
     */
    @GetMapping("list")
    public String Index(@CurrentUser User user, Model model) {
        //1.获取机构信息
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        Integer fid = user.getFid();
        if (!Constants.ADMINCODE.equals(rolecode)) { //非管理员
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);


        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);
        System.err.println(orgs);//debug
        return "page/teacher-list";
    }

    /**
     * 获取列表数据
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:04
     */
    @PostMapping("list")
    @ResponseBody
    public DataTableJson getType(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                 FamousTeacher object, @CurrentUser User user) {
        PageHelper.startPage(page, pageSize);
//        if(object.getFid()==null){
//            object.setFid(user.getFid());
//        }
        if (object.getPersonName() == "") {
            object.setPersonName(null);
        }
        List<FamousTeacher> list = famousTeacherMapper.getList(object);
        DataTableJson<FamousTeacher> dataTableJson = new DataTableJson<FamousTeacher>(list);
        return dataTableJson;
    }

    /**
     * 添加名师页面
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
        fid = 34797;//debug
        fid = fid == null ? user.getFid() : fid;
        if (!Constants.ADMINCODE.equals(rolecode)) {
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);

        model.addAttribute("fid", fid);
        model.addAttribute("orgs", orgs);
        return "page/teacher-add";
    }

    /**
     * 保存添加名师
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:01
     */
    @PostMapping("add")
    public String add(FamousTeacher object, @CurrentUser User user) {
        FamousTeacher famousTeacher = famousTeacherService.add(object, user);
        String gourl = "/page/success.html";
        if (famousTeacher == null) {
            gourl = "/page/fail.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }


    /**
     * 编辑名师页面（如果不是弹窗添加用）
     *
     * @param id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:06
     */
    @GetMapping("edit/{id}")
    public String editIndex(@PathVariable Integer id, Model model) {
        FamousTeacher famousTeacher = famousTeacherService.get(id);
        Integer fid = famousTeacher.getFid();
        List<OrganizationExtend> organizationExtends = new ArrayList<OrganizationExtend>();
        if (fid != null) {
            OrganizationExtend tempprganizationExtend = new OrganizationExtend();
            tempprganizationExtend.setFid(fid);
            organizationExtends = organizationExtendService.getList(tempprganizationExtend);
        }

        model.addAttribute("object", famousTeacher);
        model.addAttribute("orgs", organizationExtends);
        return "page/teacher-edit";
    }

    /**
     * 保存编辑名师
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:06
     */
    @PostMapping("edit")
    public String edit(FamousTeacher famousTeacher) {
        famousTeacher = famousTeacherService.update(famousTeacher);
        String gourl = "/page/fail.html";
        if (famousTeacher != null) {
            gourl = "/page/success.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }


    /**
     * 删除名师
     *
     * @param id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:15
     */
    @PostMapping("del/{id}")
    @ResponseBody
    public String del(@PathVariable Integer id) {
        int result = famousTeacherService.del(id);
        String gourl = "/page/fail.html";
        if (result > 0) {
            gourl = "/page/success.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }

    /**
     * 修改名师状态
     *
     * @param id     通知id
     * @param status 要修改为的状态0 无效  1有效
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("status/{id}/{status}")
    public String changeStatus(@PathVariable Integer id, @PathVariable Integer status) {
        FamousTeacher teacher = famousTeacherService.get(id);
        String gourl = "/page/success.html";
        if (teacher != null) {
            teacher.setStatus(status);
            teacher = famousTeacherService.update(teacher);
            if (teacher == null) {
                gourl = "/page/fail.html";
            }
        } else {
            gourl = "/page/fail.html";
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
    }

    /**
     * 首页名师推荐列表页面
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @GetMapping("indexshow")
    public String IndexShow(@CurrentUser User user, @RequestParam(required = false) Integer fid, Model model) {
        //如果pid不为0 则为子标签管理
        fid = fid == null ? (user == null ? null : user.getFid()) : fid;

        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
        if (!Constants.ADMINCODE.equals(rolecode)) {
            organizationExtend.setFid(fid);
        }
        orgs = organizationExtendService.getList(organizationExtend);
        model.addAttribute("orgs", orgs);
        model.addAttribute("fid", fid);
        return "page/homepage-teacher-list";
    }

    /**
     * 获取首页显示的名师列表页面数据
     *
     * @param fid 机构id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("indexshow/list")
    @ResponseBody
    public DataTableJson<Map<String, Object>> IndexShowData(@RequestParam Map<String, Object> searchdata, @RequestParam(required = false, defaultValue = "1") Integer page,
                                                            @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize, @RequestParam(required = false) Integer fid, Model model) {
        PageHelper.startPage(page, pageSize);

        searchdata.remove("page");
        searchdata.remove("pageSize");
        searchdata.remove("limit");
        List<Map<String, Object>> teachers;
        DataTableJson<Map<String, Object>> dataTableJson = null;
        if (fid == null) {//机构为null时 不查询数据
            fid = -1;
        }
        searchdata.put("fid", fid);
        teachers = recommendedFamousTeacherMapper.getList(searchdata);
        dataTableJson = new DataTableJson<Map<String, Object>>(teachers);


        return dataTableJson;
    }


    /**
     * 将名师添加到首页显示
     *
     * @param fid id
     * @return
     * @author YangChengLiang
     * @date 2017/11/17 15:54
     */
    @GetMapping("showIndex/add/list/{fid}")
    public String addIndex(@PathVariable Integer fid, Model model) {
        model.addAttribute("fid", fid);
        return "page/homepage-teacher-manage";
    }


    /**
     * 将名师添加到首页显示
     *
     * @param fid 结构id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("showIndex/list/{fid}")
    @ResponseBody
    public DataTableJson addIndexdata(@RequestParam Map<String, Object> searchdata, @RequestParam(required = false, defaultValue = "1") Integer page,
                                      @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize, @PathVariable Integer fid, Model model) {
        PageHelper.startPage(page, pageSize);
        searchdata.put("fid", fid);
        List<Map<String, Object>> teachers = recommendedFamousTeacherMapper.getNotInIndexList(searchdata);
        DataTableJson<Map<String, Object>> dataTableJson = new DataTableJson<Map<String, Object>>(teachers);
        return dataTableJson;
    }


    /**
     * 将名师添加到首页显示
     *
     * @param fid 结构id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @GetMapping("showIndex/list/add/{fid}")
    @ResponseBody
    public Object addIndexdata(@PathVariable Integer fid, String ids) {
        String[] teacherIds = ids.split(",");
        SubjectRType subjectRType = null;
        for (String id : teacherIds) {
            RecommendedFamousTeacher rft = new RecommendedFamousTeacher();
            rft.setFid(fid);
            rft.setTeacherId(Integer.valueOf(id));
            Map<String, Object> searchdata = new HashMap<String, Object>();
            searchdata.put("fid", fid);
            searchdata.put("id", id);
            List<Map<String, Object>> datas = recommendedFamousTeacherMapper.getList(searchdata);
            if (datas.size() == 0) {
                recommendedFamousTeacherMapper.insert(rft);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 1);
        return JSON.toJSON(result);
    }


    /**
     * 将名师 移除首页显示
     *
     * @param ids 要移除的首页推荐id
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("hideIndex")
    @ResponseBody
    public Object delIndex(String ids) {

        String[] relationIds = ids.split(",");
        for (String id : relationIds) {
            recommendedFamousTeacherMapper.deleteByPrimaryKey(Integer.valueOf(id));
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 1);
        return JSON.toJSON(result);
    }


    /**
     * 未分类名师
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @GetMapping("gorelation/{typeId}/{fid}")
    public String gorelation(@PathVariable Integer typeId, @PathVariable Integer fid, Model model) {
        model.addAttribute("typeId", typeId);
        model.addAttribute("fid", fid);
        return "page/teacher-list-relation";
    }

    /**
     * 查询当前机构中除当前分类外的名师
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:22
     */
    @PostMapping("gorelation/{tid}/{fid}")
    @ResponseBody
    public DataTableJson<FamousTeacher> gorelation(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                   @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                   @PathVariable Integer tid, @PathVariable Integer fid, FamousTeacher teacher) {
        System.out.println(teacher);
        PageHelper.startPage(page, pageSize);
        teacher.setFid(fid);
        Map<String, Object> seacherdata = new HashMap<String, Object>();
        seacherdata.put("typeId", tid);
        seacherdata.put("teacher", teacher);
        return new DataTableJson<>(famousTeacherMapper.getListNotInType(seacherdata));
    }
}
