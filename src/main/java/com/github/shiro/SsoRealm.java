package com.github.shiro;

import com.github.mapper.UserMapper;
import com.github.model.User;
import com.github.service.PermissionService;
import com.github.service.RoleService;
import com.github.util.Constants;
import com.github.util.SpringContextHolder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SsoRealm extends AuthorizingRealm {

    @Resource private UserMapper userMapper;
    @Resource private RoleService roleService;
    @Resource private PermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
    	return token instanceof SsoToken;
    }
    
    /** 认证 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        Integer uid = (Integer) token.getPrincipal();
        Integer fid = (Integer) token.getCredentials();

        // 1. SSO用户已在本系统授权或已绑定本系统用户
        User user = userMapper.getByUid(uid);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(Constants.STATUS_FALSE.equals(user.getStatus())) {
            throw new LockedAccountException(); //帐号锁定
        }

        // 2. 去SSO验证该用户已通过认证



        SpringContextHolder.getSession().setAttribute(Constants.LOGIN_USER, user);

        
        return new SimpleAuthenticationInfo(uid, fid, super.getName());
    }
    
    /** 授权 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        System.err.println(principals.getPrimaryPrincipal());
        Integer uid = (Integer) principals.getPrimaryPrincipal();
        User user = userMapper.getByUid(uid);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleService.getUserRoleStringSet(user.getId()));
        authorizationInfo.setStringPermissions(permissionService.getUserPermissionStringSet(user.getId()));
        
        return authorizationInfo;
    }
}
