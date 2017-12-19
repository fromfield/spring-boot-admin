package com.github.service;

import com.github.mapper.FamousTeacherTypeMapper;
import com.github.mapper.NoticeMapper;
import com.github.model.FamousTeacher;
import com.github.model.FamousTeacherType;
import com.github.model.Notice;
import com.github.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FamousTeacherTypeService {

    @Autowired
    private FamousTeacherTypeMapper famousTeacherTypeMapper;

    /**
     * 添加名师类型
     * @author YangChengLiang
     * @date 2017/11/8 10:58
     * @param famousTeacherType
     * @param user
     * @return
     */
    public FamousTeacherType add(FamousTeacherType famousTeacherType, User user){
        if(user==null){
            return  null;
        }
        if(famousTeacherType.getStatus()==null){//当为null的时候,设置为“0”
            famousTeacherType.setStatus("0");
        }
        famousTeacherTypeMapper.insert(famousTeacherType);
        return  famousTeacherType;
    }

    /**
     * 根据id获取名师类型
     *  @author YangChengLiang
     * @date 2017/11/8 11:00
     * @param id
     * @return
     */
    public FamousTeacherType get(Integer id){
        return  famousTeacherTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新名师类型
     * @author YangChengLiang
     * @date 2017/11/8 11:06
     * @param famousTeacherType
     * @return
     */
    public FamousTeacherType update(FamousTeacherType famousTeacherType){
        if(famousTeacherType.getStatus()==null){//当为null的时候,设置为“0”
            famousTeacherType.setStatus("0");
        }
        famousTeacherTypeMapper.updateByPrimaryKey(famousTeacherType);
        return  famousTeacherType;
    }

    /**
     * 删除 如果该标签下无数据  可以删除否者不可以删除（待补充）
     *  @author YangChengLiang
     * @date 2017/11/8 16:35
     * @param id
     */
    public int del(Integer id){
        return famousTeacherTypeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据fid获取列表数据
     * @param fid
     * @return
     */
    public List<Map<String,Object>> getTree(Integer fid){
        return famousTeacherTypeMapper.getTree(fid);
    }

}
