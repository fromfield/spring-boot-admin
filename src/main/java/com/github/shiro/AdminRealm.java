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
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class AdminRealm extends AuthorizingRealm {

    @Resource private UserMapper userMapper;
    @Resource private RoleService roleService;
    @Resource private PermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
    	return token instanceof UsernamePasswordToken;
    }
    
    /** 认证 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String userName = (String) token.getPrincipal();
        User user = userMapper.getByUserName(userName);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(Constants.STATUS_FALSE.equals(user.getStatus())) {
            throw new LockedAccountException(); //帐号锁定
        }
        SpringContextHolder.getSession().setAttribute(Constants.LOGIN_USER, user);
        
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配, 如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), super.getName());
        return authenticationInfo;
    }
    
    /** 授权 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userName = (String) principals.getPrimaryPrincipal();
        User user = userMapper.getByUserName(userName);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleService.getUserRoleStringSet(user.getId()));
        authorizationInfo.setStringPermissions(permissionService.getUserPermissionStringSet(user.getId()));
        
        return authorizationInfo;
    }
}
