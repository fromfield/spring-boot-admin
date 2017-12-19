package com.github.service;

import com.github.mapper.NoticeTypeMapper;
import com.github.model.NoticeType;
import com.github.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeTypeService{

    @Autowired
    private NoticeTypeMapper noticeTypeMapper;

    /**
     * 添加通知类型
     * @author YangChengLiang
     * @date 2017/11/8 10:58
     * @param noticeType
     * @param user
     * @return
     */
    public NoticeType add(NoticeType noticeType, User user){
        if(user==null){
            return  null;
        }
        if(noticeType.getStatus()==null){//当为null的时候,设置为“0”
            noticeType.setStatus("0");
        }
        noticeType.setCreateTime(new Date());
        noticeType.setCreateUserId(user.getId());
        noticeTypeMapper.insert(noticeType);
        return  noticeType;
    }

    /**
     * 根据id获取通知类型
     *  @author YangChengLiang
     * @date 2017/11/8 11:00
     * @param id
     * @return
     */
    public NoticeType get(Integer id){
        return  noticeTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新通知类型
     * @author YangChengLiang
     * @date 2017/11/8 11:06
     * @param noticeType
     * @return
     */
    public NoticeType update(NoticeType noticeType,User user){
        if(user==null){
            return  null;
        }
        if(noticeType.getStatus()==null){//当为null的时候,设置为“0”
            noticeType.setStatus("0");
        }
        noticeType.setModifyTime(new Date());
        noticeType.setModifyUserId(user.getId());
        noticeTypeMapper.updateByPrimaryKey(noticeType);
        return  noticeType;
    }

    /**
     * 删除 如果该标签下无数据  可以删除否者不可以删除（待补充）
     *  @author YangChengLiang
     * @date 2017/11/8 16:35
     * @param id
     */
    public int del(Integer id){
        return noticeTypeMapper.deleteByPrimaryKey(id);
    }

}
