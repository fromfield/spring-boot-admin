package com.github.service;

import com.alibaba.fastjson.JSONObject;
import com.github.mapper.FamousTeacherMapper;
import com.github.model.FamousTeacher;
import com.github.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamousTeacherService {

    @Autowired
    private FamousTeacherMapper famousTeacherMapper;
    @Autowired
    private ApiService apiService;
    /**
     * 添加名师
     * @author YangChengLiang
     * @date 2017/11/8 10:58
     * @param famousTeacher
     * @param user
     * @return
     */
    public FamousTeacher add(FamousTeacher famousTeacher, User user){
        JSONObject jsonObject=apiService.getFanyaCourseId(famousTeacher.getSubjectId());
        if(jsonObject!=null){
            famousTeacher.setCover(jsonObject.getString("imageurl"));
            famousTeacher.setPersonName(jsonObject.getString("name"));
//            famousTeacher.setDescription(jsonObject.getString("schools"));
//            famousTeacher.setSubjectId(jsonObject.getString("teacherfactor"));
            if(famousTeacher.getFid()==null&&user!=null){
                famousTeacher.setFid(user.getFid());
            }
            if(famousTeacher.getStatus()==null){//当为null的时候,设置为“0”
                famousTeacher.setStatus(0);
            }
            famousTeacherMapper.insert(famousTeacher);
            return  famousTeacher;
        }else{
            return null;
        }
    }

    /**
     * 根据id获取名师
     *  @author YangChengLiang
     * @date 2017/11/8 11:00
     * @param id
     * @return
     */
    public FamousTeacher get(Integer id){
        return  famousTeacherMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新名师类型
     * @author YangChengLiang
     * @date 2017/11/8 11:06
     * @param famousTeacher
     * @return
     */
    public FamousTeacher update(FamousTeacher famousTeacher){
        FamousTeacher newteacher=famousTeacherMapper.selectByPrimaryKey(famousTeacher.getId());
        if(newteacher!=null){
            if(newteacher.getSubjectId()!=famousTeacher.getSubjectId()){
                JSONObject jsonObject=apiService.getFanyaCourseId(famousTeacher.getSubjectId());
                if(jsonObject!=null){
                    famousTeacher.setCover(jsonObject.getString("imageurl"));
                    famousTeacher.setPersonName(jsonObject.getString("name"));
//                    famousTeacher.setDescription(jsonObject.getString("schools"));
                    famousTeacher.setFid(newteacher.getFid());
                }
            }
            if(famousTeacher.getStatus()==null){//当为null的时候,设置为“0”
                famousTeacher.setStatus(0);
            }
            famousTeacherMapper.updateByPrimaryKey(famousTeacher);
            return  famousTeacher;
        }else{
            return  null;
        }
    }

    /**
     * 删除 如果该标签下无数据  可以删除否者不可以删除（待补充）
     *  @author YangChengLiang
     * @date 2017/11/8 16:35
     * @param id
     */
    public int del(Integer id){
        return famousTeacherMapper.deleteByPrimaryKey(id);
    }

}
