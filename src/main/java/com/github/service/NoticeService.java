package com.github.service;

import com.github.mapper.NoticeMapper;
import com.github.mapper.NoticeTypeMapper;
import com.github.model.Notice;
import com.github.model.NoticeType;
import com.github.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 添加通知
     * @author YangChengLiang
     * @date 2017/11/8 10:58
     * @param notice
     * @param user
     * @return
     */
    public Notice add(Notice notice, User user){
        if(user==null){
            return  null;
        }
        if(notice.getStatus()==null){//当为null的时候,设置为“0”
            notice.setStatus("0");
        }
        notice.setCreateTime(new Date());
        notice.setCreateUserId(user.getId());
        noticeMapper.insert(notice);
        return  notice;
    }

    /**
     * 根据id获取通知
     *  @author YangChengLiang
     * @date 2017/11/8 11:00
     * @param id
     * @return
     */
    public Notice get(Integer id){
        return  noticeMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新通知
     * @author YangChengLiang
     * @date 2017/11/8 11:06
     * @param notice
     * @return
     */
    public Notice update(Notice notice,User user){
        if(user==null){
            return  null;
        }
        if(notice.getStatus()==null){//当为null的时候,设置为“0”
            notice.setStatus("0");
        }
        notice.setModifyTime(new Date());
        notice.setModifyUserId(user.getId());
        noticeMapper.updateByPrimaryKey(notice);
        return  notice;
    }

    /**
     * 删除 如果该标签下无数据  可以删除否者不可以删除（待补充）
     *  @author YangChengLiang
     * @date 2017/11/8 16:35
     * @param id
     */
    public int del(Integer id){
        return noticeMapper.deleteByPrimaryKey(id);
    }

}
