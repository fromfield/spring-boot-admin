package com.github.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.mapper.BaseMapper;
import com.github.mapper.EcnuCourseMapper;
import com.github.model.EcnuCourse;
import com.github.pagehelper.PageHelper;
import com.github.util.Constants;
import com.github.util.CourseFidUtil;
import com.github.util.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseService extends BaseService<EcnuCourse>{

    @Autowired
    private EcnuCourseMapper dao;

    public List<EcnuCourse> selectByCondition(EcnuCourse course,Integer page,Integer pageSize){
        if(course != null){

            Integer fid = CourseFidUtil.getFid();
            course.setFid(fid);
            //只查询已发布状态的课程
            course.setStatus(Constants.COURSE_STATUS_PUBLISHED);
            PageHelper.startPage(page, pageSize);
            return dao.selectByCondition(course);
        }
        return null;
    }

    public EcnuCourse getCourseDetailByCourseId(Integer courseId) throws Exception {
        EcnuCourse course = null;
        if(courseId != null) {
            HttpRequest httpRequest = new HttpRequest();
            String result = "";
            String url = Constants.GRT_COURSE_DETAIL_HREF.replace("{ids}", courseId.toString());
            result = httpRequest.doGet(url);
            JSONArray jsonArray = JSONObject.parseObject(result).getJSONArray("data");
            if(jsonArray.size()>0){
                course = new EcnuCourse();
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                course.setFid(CourseFidUtil.getFid());
                course.setCourseId(courseId);
                course.setCover(jsonObject.getString("imageurl"));
                course.setName(jsonObject.getString("name"));
                course.setDescription(jsonObject.getString("schools"));
                course.setUname(jsonObject.getString("teacherfactor"));
                course.setAverage(Constants.COURSE_DEFAULT_AVERAGE);
                course.setEnroll(Constants.COURSE_DEFAULT_ENROLL);
                course.setStatus(Constants.COURSE_DEFAULT_STATUS);
                course.setOnline(Constants.COURSE_DEFAULT_ONLINE);
                course.setHour(Constants.COURSE_DEFAULT_HOUR);
                course.setCreateTime(new Date());
            }

        }
        return course;
    }

    public int insertByCourseId(Integer courseId) throws Exception {
        EcnuCourse course = getCourseDetailByCourseId(courseId);
        if(course != null){
           return dao.insertSelective(course);
        }
        return 0;
    }

    public int deleteBatch(String ids){
        if(StringUtils.isNotBlank(ids)){
            String []array = StringUtils.split(ids,",");
            List<Integer> list = new ArrayList<>();
            for (int index=0;index<array.length;index++){
                list.add(Integer.valueOf(array[index]));
            }
            return dao.deleteBatch(list);
        }
        return 0;
    }

    public List<EcnuCourse> selectByTypeId(EcnuCourse course, Integer typeId, Integer page, Integer pageSize) {
        List<EcnuCourse> courses = null;
        if(course != null && typeId != null){
            course.setTypeId(typeId);
            Integer fid = CourseFidUtil.getFid();
            course.setFid(fid);
            //只查询已发布状态的课程
            course.setStatus(Constants.COURSE_STATUS_PUBLISHED);
            PageHelper.startPage(page, pageSize);
            return dao.selectByTypeId(course);
        }
        return courses;
    }

    public List<EcnuCourse> selectByNoneTypeId(EcnuCourse course, Integer typeId, Integer page, Integer pageSize) {
        List<EcnuCourse> courses = null;
        if(course != null && typeId != null){
            course.setTypeId(typeId);
            Integer fid = CourseFidUtil.getFid();
            course.setFid(fid);
            //只查询已发布状态的课程
            course.setStatus(Constants.COURSE_STATUS_PUBLISHED);
            PageHelper.startPage(page, pageSize);
            return dao.selectByNoneTypeId(course);
        }
        return courses;
    }

    public List<EcnuCourse> selectRecommend(EcnuCourse course,boolean isRecommend, Integer page, Integer pageSize) {
        List<EcnuCourse> courses = null;
        Integer fid = CourseFidUtil.getFid();
        course.setFid(fid);
        //只查询已发布状态的课程
        course.setStatus(Constants.COURSE_STATUS_PUBLISHED);
        course.setOnline(Constants.COURSE_ONLINE);
        PageHelper.startPage(page, pageSize);
        if(isRecommend){
            courses = dao.selectHomeRecommend(course);
        }else{
            courses = dao.selectHomeNoneRecommend(course);
        }
        return courses;
    }

    @Override
    protected BaseMapper getBaseDao() {
        return dao;
    }


}
