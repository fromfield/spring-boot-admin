package com.github.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.mapper.RecommendedSubjectMapper;
import com.github.mapper.SubjectMapper;
import com.github.mapper.SubjectRTypeMapper;
import com.github.mapper.SubjectTypeMapper;
import com.github.model.*;
import com.github.pagehelper.PageHelper;
import com.github.service.RoleService;
import com.github.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author fromfield
 * @create 2017-11-07 10:17
 **/

@Controller
@RequestMapping("subject")
public class SubjectController {

    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private SubjectTypeMapper subjectTypeMapper;
    @Resource
    private SubjectRTypeMapper subjectRTypeMapper;
    @Autowired
    private RoleService roleService;
    @Resource
    private RecommendedSubjectMapper recommendedSubjectMapper;

    @GetMapping("{pid}/add-subject-type")
    public String addSubjectType(@PathVariable("pid") Integer pid, Model model, Integer type){
        model.addAttribute("pid",pid);
        model.addAttribute("type",type);
        return "page/subject-type-add";
    }

    /**
     * 增加分类
     * @param type
     * @param subjectType
     * @return
     */
    @PostMapping("add-subject-type")
    public String addSubjectType(SubjectType subjectType, Integer type){
        subjectType.setFid(CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID)));
        subjectType.setSequence(subjectTypeMapper.selectMaxSeqByPid(subjectType) + 1);
        subjectType.setStatus(1);
        subjectTypeMapper.insertSelective(subjectType);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 删除分类
     * @param typeId
     * @return
     */
    @RequestMapping(value = "{typeId}/delete", method = RequestMethod.GET)
    public String deleteSubjectType(@PathVariable("typeId") Integer typeId){
        subjectTypeMapper.deleteByPrimaryKey(typeId);
        subjectRTypeMapper.deleteByTypeId(typeId);//删除分类的同时删除该类型和资源的关联关系
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @RequestMapping("{typeId}/edit-subject-type")
    public Object editSubjectTYpe(@PathVariable("typeId") Integer id, Model model) {
        model.addAttribute("subjectType", subjectTypeMapper.selectByPrimaryKey(id));
        return "page/subject-type-edit";
    }
    /**
     * 修改分类
     * @param subjectType
     * @return
     */
    @PostMapping("update-subject-type")
    public String updateSubjectType(SubjectType subjectType){
        subjectTypeMapper.updateByPrimaryKeySelective(subjectType);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 根据类型父id获取子类型列表（最上层类型pid：1、图书；2、期刊；3、专题）
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping("{type}/{pid}/subject-types-by-pid")
    public DataTableJson<SubjectType> getSubjectTypesByPid(@PathVariable("pid") Integer pid,
                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                  SubjectType subjectType, @PathVariable("type") Integer type){
        Integer fid = CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID));
        PageHelper.startPage(page, pageSize);
        subjectType.setPid(pid);
        subjectType.setFid(fid);
        return new DataTableJson<>(subjectTypeMapper.getList(subjectType));
    }

    @RequestMapping("child/list/{pid}")
    public String redirectToAddPage(@PathVariable("pid") Integer pid, Model model, Integer type) {
        model.addAttribute("pid", pid);
        model.addAttribute("type", type);
        return "page/subject-type-child-list";
    }

    /**
     * 获取图书、期刊、专题类型树形列表（最上层类型pid：1、图书；2、期刊；3、专题）
     */
    @ResponseBody
    @GetMapping("{type}/{pid}/subject-type-list-by-pid")
    public List<Map<String, Object>> getSubjectListByPid(@PathVariable("pid") Integer pid, @PathVariable("type") Integer type){
        SubjectType s = new SubjectType();
        s.setPid(pid);
        s.setFid(CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID)));
        List<Map<String, Object>> result = subjectTypeMapper.getMap(s);
        for(Map<String, Object> map : result){
            s.setPid((Integer) map.get("id"));
            map.put("children", subjectTypeMapper.getMap(s));
        }
        return result;
    }







    @ResponseBody
    @RequestMapping("{type}/subject-list")
    public DataTableJson<Subject> subjectList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                              @CurrentUser User loginUser, @PathVariable("type") Integer type, Subject subject,
                                              Integer fid, HttpServletRequest request){
        //如果是超级管理员，则将当前的fid放入Session
        if(roleService.isSuperAdmin(loginUser)){
            if(fid != null) {
                request.getSession().setAttribute(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                        (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID), fid);
            }else{
                request.getSession().setAttribute(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                        (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID), null);
            }
        }
        PageHelper.startPage(page, pageSize);
        subject.setType(type);
        subject.setFid(fid == null ? loginUser.getFid() : fid);
        return new DataTableJson<>(subjectMapper.getList(subject));
    }

    @PostMapping("add-subject")
    public String addSubject(Subject subject){
        Integer type = subject.getType();
        subject.setFid(CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID)));
        subject.setStatus(1);
        subjectMapper.insertSelective(subject);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @RequestMapping("{id}/delete-subject")
    public String deleteSubject(@PathVariable("id") Integer id){
        subjectMapper.deleteByPrimaryKey(id);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @RequestMapping("delete-subjects")
    public String deleteSubjects(String subjectIds){
        String[] ids = subjectIds.split(",");
        for(String id : ids){
            subjectMapper.deleteByPrimaryKey(Integer.parseInt(id));
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @RequestMapping("{id}/edit-subject")
    public Object edit(@PathVariable Integer id, Model model) {
        model.addAttribute("subject", subjectMapper.selectByPrimaryKey(id));
        return "page/subject-edit";
    }

    @PostMapping("update-subject")
    public String updateSubject(Subject subject) {
        subjectMapper.updateByPrimaryKeySelective(subject);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @PostMapping("{type}/preview")
    public String preview(@PathVariable("type") Integer type, String dataId, Model model) throws Exception {
        //type：1、图书，2、期刊，3、专题
        try {
            String requestUrl = type == Constants.QIKAN_TYPE
                    ? Constants.QIKAN_DETAIL_HREF.replace("{resId}", dataId)
                    : Constants.GRT_COURSE_DETAIL_HREF.replace("{ids}", dataId);
            JSONObject jsonObject = JSONObject.parseObject(new HttpRequest().doGet(requestUrl)).getJSONArray(type == Constants.QIKAN_TYPE ? "results" : "data").getJSONObject(0);
            Subject subject = new Subject();
            subject.setType(type);
            subject.setDataId(dataId);
            subject.setAuthor(type == Constants.QIKAN_TYPE ? jsonObject.getString("unit") : jsonObject.getString("teacherfactor"));
            subject.setCover(type == Constants.QIKAN_TYPE ? jsonObject.getString("coverurl") : jsonObject.getString("imageurl"));
            subject.setTitle(type == Constants.QIKAN_TYPE ? jsonObject.getString("magname") : jsonObject.getString("name"));
            subject.setJourUrl(type == Constants.QIKAN_TYPE ? jsonObject.getString("mobileulr").replace("#INNER", "&from=space") : "");
            subject.setHour(Constants.SUBJECT_DEFAULT_HOUR);
            subject.setOnline(Constants.SUBJECT_ONLINE);
            model.addAttribute("subject", subject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "page/subject-preview";
    }







    /**
     * 类型资源关联
     * @param typeId 类型id
     * @param subjectIds 例(1,2,3,4,5...)
     * @return
     */
    @ResponseBody
    @GetMapping("{typeId}/add-subject-r-type")
    public DataTableJson addSubjectRType(@PathVariable("typeId") Integer typeId, String subjectIds){
        String[] ids = subjectIds.split(",");
        SubjectRType subjectRType = null;
        for(String id : ids){
            subjectRType = new SubjectRType();
            subjectRType.setTypeId(typeId);
            subjectRType.setSubjectId(Integer.parseInt(id));
            subjectRTypeMapper.insertSelective(subjectRType);
        }
        DataTableJson d = new DataTableJson();
        d.setCode(0);
        return d;
    }

    /**
     * 删除资源类型关联
     * @param typeId
     * @param subjectIds 例1,2,3,4,5...
     * @return
     */
    @GetMapping("{typeId}/delete-subject-r-type")
    public String deleteSubjectRTypes(@PathVariable("typeId") Integer typeId, String subjectIds){
        String[] ids = subjectIds.split(",");
        SubjectRType subjectRType = null;
        for(String id : ids){
            subjectRType = new SubjectRType();
            subjectRType.setTypeId(typeId);
            subjectRType.setSubjectId(Integer.parseInt(id));
            subjectRTypeMapper.deleteByTypeIdAndSubjectId(subjectRType);
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 资源分类下关联的资源数据
     * @param page
     * @param pageSize
     * @param typeId
     * @param type
     * @param subject
     * @return
     */
    @ResponseBody
    @RequestMapping("{type}/{typeId}/type-r-subject-list")
    public DataTableJson<Subject> selectTypeRSubject(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                              @PathVariable("typeId") Integer typeId, @PathVariable("type") Integer type, Subject subject){
        PageHelper.startPage(page, pageSize);
        subject.setType(type);
        return new DataTableJson<>(subjectMapper.selectTypeRSubject(typeId, subject));
    }


    /**
     * 未关联的分类数据
     * @param page
     * @param pageSize
     * @param typeId
     * @param type
     * @param subject
     * @return
     */
    @ResponseBody
    @RequestMapping("{type}/{typeId}/type-not-r-subject-list")
    public DataTableJson<Subject> selectTypeNotRSubject(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                     @PathVariable("typeId") Integer typeId, @PathVariable("type") Integer type, Subject subject){
        Integer fid = CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID));
        PageHelper.startPage(page, pageSize);
        subject.setType(type);
        subject.setFid(fid);
        return new DataTableJson<>(subjectMapper.selectTypeNotRSubject(typeId, subject));
    }








    /**
     * 获取首页推荐图书\期刊列表
     * @return
     */
    @ResponseBody
    @RequestMapping("{type}/homepage-recommended")
    public DataTableJson<Subject> homePageRecommended(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                           @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                           Subject subject, @PathVariable("type") Integer type){
        Integer fid = CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID));
        PageHelper.startPage(page, pageSize);
        subject.setType(type);
        subject.setFid(fid);
        return new DataTableJson<>(subjectMapper.homePageRecommended(subject));
    }

    @ResponseBody
    @RequestMapping("{type}/homepage-not-recommended")
    public DataTableJson<Subject> homePageNotRecommended(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                        @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                        @PathVariable("type") Integer type, Subject subject){
        Integer fid = CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID));
        PageHelper.startPage(page, pageSize);
        subject.setType(type);
        subject.setFid(fid);
        return new DataTableJson<>(subjectMapper.homePageNotRecommended(subject));
    }

    /**
     * 删除首页推荐图书\期刊
     * @param subjectIds 例1,2,3,4,5...
     */
    @GetMapping("{type}/delete-homepage-rec")
    public String deleteByFidAndSubjectId(@PathVariable("type") Integer type, String subjectIds){
        Integer fid = CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID));
        String[] ids = subjectIds.split(",");
        RecommendedSubject r = null;
        for(String id : ids){
            r = new RecommendedSubject();
            r.setFid(fid);
            r.setSubjectId(Integer.parseInt(id));
            recommendedSubjectMapper.deleteByFidAndSubjectId(r);
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 添加机构首页subject
     * @param subjectIds 例(1,2,3,4,5...)
     * @return
     */
    @ResponseBody
    @GetMapping("{type}/add-homepage-rec")
    public DataTableJson addRecommededSubject(@PathVariable("type") Integer type, String subjectIds){
        Integer fid = CourseFidUtil.getSubjectFid(type.intValue() == Constants.BOOK_TYPE ? Constants.BOOK_PAGE_FID :
                (type.intValue() == Constants.QIKAN_TYPE ? Constants.QIKAN_PAGE_FID : Constants.ZHUANTI_PAGE_FID));
        String[] ids = subjectIds.split(",");
        RecommendedSubject r = null;
        for(String id : ids){
            r = new RecommendedSubject();
            r.setFid(fid);
            r.setSubjectId(Integer.parseInt(id));
            recommendedSubjectMapper.insertSelective(r);
        }
        DataTableJson d = new DataTableJson();
        d.setCode(0);
        return d;
    }
}
