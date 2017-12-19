package com.github.controller;

import com.github.mapper.RecommendedCourseMapper;
import com.github.model.EcnuCourse;
import com.github.model.OrganizationExtend;
import com.github.model.RecommendedCourse;
import com.github.model.User;
import com.github.service.CourseService;
import com.github.service.OrganizationExtendService;
import com.github.service.RoleService;
import com.github.util.Constants;
import com.github.util.CourseFidUtil;
import com.github.util.CurrentUser;
import com.github.util.DataTableJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService service;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RecommendedCourseMapper recommendedCourseMapper;

    @ResponseBody
    @RequestMapping({"", "/", "list"})
    public DataTableJson<EcnuCourse> list(@RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                          EcnuCourse course, Integer fid, HttpServletRequest request,
                                          @CurrentUser User user) {
        //如果是超级管理员，则将当前的fid放入Session,以便后面取出使用
        if(roleService.isSuperAdmin(user)){
            if(fid != null) {
                request.getSession().setAttribute(Constants.COURSE_PAGE_FID, fid);
            }else{
                request.getSession().setAttribute(Constants.COURSE_PAGE_FID, null);
            }
        }
        List<EcnuCourse> courses = service.selectByCondition(course,page,pageSize);
        DataTableJson<EcnuCourse> dataTableJson = new DataTableJson<>(courses);
        return dataTableJson;
    }

    @ResponseBody
    @RequestMapping("{id}/delete")
    public Object delete(@PathVariable("id") Integer id){
        if(id != null){
            EcnuCourse course = new EcnuCourse();
            course.setId(id);
            //软删除
            course.setStatus(Constants.COURSE_STATUS_DELETE);
           int count = service.updateByPrimaryKeySelective(course);
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("batch/delete")
    public Object deleteBatch(String ids){
        if(StringUtils.isNotBlank(ids)){
            //软删除
            int count = service.deleteBatch(ids);
        }
        return "";
    }

    @RequestMapping("{id}/edit")
    public Object edit(@PathVariable Integer id, Model model) {
        model.addAttribute("course", service.selectByPrimaryKey(id));
        return "page/course-edit";
    }

    @PostMapping("update")
    public String update(EcnuCourse course) {
        course.setUpdateTime(new Date());
        int count = service.updateByPrimaryKeySelective(course);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @PostMapping("add")
    public String add( Integer courseId) throws Exception {
        int count = service.insertByCourseId(courseId);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @PostMapping("preview")
    public String preview(Integer courseId,Model model) throws Exception {
        EcnuCourse course = service.getCourseDetailByCourseId(courseId);
        model.addAttribute("course", course);
        return "page/course-preview";
    }

    /**
     * 获取当前分类下的课程
     * @param page
     * @param pageSize
     * @param typeId
     * @param course
     * @return
     */
    @ResponseBody
    @RequestMapping("typeList/{typeId}")
    public DataTableJson<EcnuCourse> typeList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                          @PathVariable("typeId") Integer typeId,
                                          EcnuCourse course) {
        List<EcnuCourse> courses = service.selectByTypeId(course,typeId,page,pageSize);
        DataTableJson<EcnuCourse> dataTableJson = new DataTableJson<>(courses);
        return dataTableJson;
    }

    /**
     * 获取当前未与当前分类关联的分类
     * @param page
     * @param pageSize
     * @param typeId
     * @param course
     * @return
     */
    @ResponseBody
    @RequestMapping("noneTypeList/{typeId}")
    public DataTableJson<EcnuCourse> noneTypeList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                              @PathVariable("typeId") Integer typeId,
                                              EcnuCourse course) {
        List<EcnuCourse> courses = service.selectByNoneTypeId(course,typeId,page,pageSize);
        DataTableJson<EcnuCourse> dataTableJson = new DataTableJson<>(courses);
        return dataTableJson;
    }

    /**
     * 跳转页面到分类关理添加页面
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("type/add/{typeId}")
    public String redirectToTypeAdd(@PathVariable("typeId") Integer typeId, Model model) {
        model.addAttribute("typeId",typeId);
        return "page/course-type-manage-add";
    }

    @ResponseBody
    @RequestMapping("home/recommend/list")
    public DataTableJson<EcnuCourse> homeRecommendList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                              EcnuCourse course) {
        List<EcnuCourse> courses = service.selectRecommend(course,true,page,pageSize);
        DataTableJson<EcnuCourse> dataTableJson = new DataTableJson<>(courses);
        return dataTableJson;
    }

    @ResponseBody
    @RequestMapping("home/none/recommend/list")
    public DataTableJson<EcnuCourse> homeNoneRecommendList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                       @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                       EcnuCourse course) {
        List<EcnuCourse> courses = service.selectRecommend(course,false,page,pageSize);
        DataTableJson<EcnuCourse> dataTableJson = new DataTableJson<>(courses);
        return dataTableJson;
    }

    @ResponseBody
    @RequestMapping("home/recommend/delete/{id}")
    public Object homeRecommendDelete(@PathVariable("id")Integer id) {
        int count = recommendedCourseMapper.deleteByFidAndCourseId(CourseFidUtil.getFid(),id);
        return "";
    }


    @ResponseBody
    @RequestMapping("home/recommend/add")
    public Object homeRecommendAdd(String ids) {
        String []courseIds = StringUtils.split(ids,",");
        for(String id : courseIds) {
            RecommendedCourse recommendedCourse = new RecommendedCourse();
            recommendedCourse.setCourseId(Integer.parseInt(id));
            recommendedCourse.setFid(CourseFidUtil.getFid());
            recommendedCourseMapper.insertSelective(recommendedCourse);
        }
        return "";
    }
}
