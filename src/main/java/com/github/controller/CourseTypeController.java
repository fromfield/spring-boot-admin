package com.github.controller;

import com.github.model.CourseType;
import com.github.service.CourseTypeService;
import com.github.util.DataTableJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.List;

@Controller
@RequestMapping("courseType")
public class CourseTypeController {

    @Autowired
    private CourseTypeService service;

    @ResponseBody
    @RequestMapping({"", "/", "list"})
    public DataTableJson<CourseType> list(@RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                          CourseType CourseType) {
        List<CourseType> roleList = service.selectTypeByCondition(CourseType,page,pageSize);
        DataTableJson<CourseType> dataTableJson = new DataTableJson<>(roleList);
        return dataTableJson;
    }

    @ResponseBody
    @RequestMapping("{id}/delete")
    public Object delete(@PathVariable("id") Integer id){
        if(id != null){
            int count = service.delete(id);
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("batch/delete")
    public Object deleteBatch(String ids){
        if(StringUtils.isNotBlank(ids)){
            //物理删除
            //int count = service.deleteBatch(ids);
        }
        return "";
    }

    @RequestMapping("add/{parentId}")
    public String redirectToChildPage(@PathVariable("parentId") Integer parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "page/course-type-add";
    }

    @RequestMapping("child/list/{parentId}")
    public String redirectToAddPage(@PathVariable("parentId") Integer parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "page/course-type-child-list";
    }

    @RequestMapping("{id}/edit")
    public Object edit(@PathVariable Integer id, Model model) {
        model.addAttribute("courseType", service.selectByPrimaryKey(id));
        return "page/course-type-edit";
    }

    @PostMapping("update")
    public String update(CourseType courseType) {
        int count = service.updateByPrimaryKeySelective(courseType);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @PostMapping("add")
    public String add( CourseType courseType) throws Exception {
        int count = service.add(courseType);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }


    @ResponseBody
    @RequestMapping("tree")
    public List<CourseType> getTypeTree(){
        List<CourseType> courseTypes = service.selectAllType();
        return courseTypes;
    }

}
