package com.github.controller;

import com.alibaba.fastjson.JSON;
import com.github.mapper.FamousTeacherMapper;
import com.github.mapper.FamousTeacherTypeRelationMapper;
import com.github.model.FamousTeacherTypeRelation;
import com.github.model.OrganizationExtend;
import com.github.model.SubjectRType;
import com.github.model.User;
import com.github.pagehelper.PageHelper;
import com.github.service.FamousTeacherService;
import com.github.service.OrganizationExtendService;
import com.github.util.Constants;
import com.github.util.CurrentUser;
import com.github.util.DataTableJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("teacher/relation")
public class TeacherRelationController {
    @Resource
    FamousTeacherMapper famousTeacherMapper;
    @Resource
    FamousTeacherService famousTeacherService;
    @Resource
    private OrganizationExtendService organizationExtendService;

    @Resource
    private FamousTeacherTypeRelationMapper famousTeacherTypeRelationMapper;

    /**
     * 主页
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 15:58
     */
    @GetMapping({"", "/", "index"})
    public String Index(@CurrentUser User user, @RequestParam(required = false) Integer fid,Model model) {
        //1.获取机构信息
        List<OrganizationExtend> orgs = null;
        OrganizationExtend organizationExtend = new OrganizationExtend();
        String rolecode = "su";//debug
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
        return "page/teacher-relation";
    }


    /**
     * 关联关系中获取名师列表页面
     *
     * @param tid
     * @param model
     * @return
     * @author YangChengLiang
     * @date 2017/11/10 17:11
     */
    @GetMapping("teacher/{tid}")
    @ResponseBody
    public DataTableJson<Map<String, Object>> getTeacher(@PathVariable Integer tid, @RequestParam Map<String,Object> searchdata, @RequestParam(required = false, defaultValue = "1") Integer page,
                                                         @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize, Model model) {
        PageHelper.startPage(page, pageSize);
        searchdata.put("tid",tid);
        searchdata.remove("page");
        searchdata.remove("pageSize");
        searchdata.remove("limit");
        List<Map<String, Object>> teachers = famousTeacherTypeRelationMapper.getAllListInfo(searchdata);
        DataTableJson<Map<String, Object>> dataTableJson = new DataTableJson<Map<String, Object>>(teachers);
        return dataTableJson;
    }

    /**
     * 删除名师类型
     *
     * @return
     * @author YangChengLiang
     * @date 2017/11/6 16:15
     */
    @PostMapping("del")
    @ResponseBody
    public String delete(String ids) {
        String[] relationIds = ids.split(",");
        SubjectRType subjectRType = null;
        for(String id : relationIds){
            famousTeacherTypeRelationMapper.delete(Integer.valueOf(id));
        }
//        String gourl = "/page/success.html";
//        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + gourl;
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("code",1);
        return JSON.toJSONString(result);
    }

    /**
     * 类型资源关联
     * @param typeId 类型id
     * @param ids 例(1,2,3,4,5...)
     * @return
     */
    @ResponseBody
    @GetMapping("add-relation/{typeId}")
    public DataTableJson addSubjectRType(@PathVariable("typeId") Integer typeId, String ids){
        String[] teaherIds = ids.split(",");
        FamousTeacherTypeRelation tempRelation = null;
        for(String id : teaherIds){
            tempRelation = new FamousTeacherTypeRelation();
            tempRelation.setTypeId(typeId);
            tempRelation.setTeacherId(Integer.parseInt(id));
            famousTeacherTypeRelationMapper.add(tempRelation);
        }
        DataTableJson d = new DataTableJson();
        d.setCode(0);
        return d;
    }


}
