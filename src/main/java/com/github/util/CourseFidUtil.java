package com.github.util;

import com.github.model.User;
import com.github.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CourseFidUtil {

    public static String getCookieByName(String name){
        if(StringUtils.isNotBlank(name)) {
            HttpServletRequest request = SpringContextHolder.getRequest();
            Cookie[] cookies = request.getCookies();
            for (int index = 0; index < cookies.length; index++) {
                if (cookies[index].getName().equals(name)) {
                    return cookies[index].getValue();
                }
            }
        }
        return null;
    }

    /**
     * 获取Fid,可更改此处获取的逻辑，实现超级管理员
     * @return
     */
    public static Integer getFid(){
        User user = (User) SpringContextHolder.getSession().getAttribute(Constants.LOGIN_USER);
        RoleService roleService = SpringContextHolder.getBean(RoleService.class);
        if(roleService.isSuperAdmin(user)){//判断是否是超级管理员
            HttpServletRequest request = SpringContextHolder.getRequest();
            Integer fid = (Integer) request.getSession().getAttribute(Constants.COURSE_PAGE_FID);
            if(fid != null){
                return fid;
            }
        }
        String fidStr = getCookieByName(Constants.FID);
        if(StringUtils.isNotBlank(fidStr)){
            return Integer.parseInt(fidStr);
        }else{
            return user.getFid();
        }
    }

    public static Integer getSubjectFid(String type){
        User user = (User) SpringContextHolder.getSession().getAttribute(Constants.LOGIN_USER);
        RoleService roleService = SpringContextHolder.getBean(RoleService.class);
        if(roleService.isSuperAdmin(user)){//判断是否是超级管理员
            HttpServletRequest request = SpringContextHolder.getRequest();
            Integer fid = (Integer) request.getSession().getAttribute(type);
            if(fid != null){
                return fid;
            }
        }
        return user.getFid();
    }

    public static Integer getUid(){
        String uidStr = getCookieByName(Constants.UID);
        if(StringUtils.isNotBlank(uidStr)){
            return Integer.parseInt(uidStr);
        }
        return null;
    }

}
