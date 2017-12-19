package com.github.service;

import com.github.mapper.RoleMapper;
import com.github.model.Role;
import com.github.model.User;
import com.github.util.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

	@Resource private RoleMapper roleMapper;

	public Set<String> getUserRoleStringSet(Integer userId) {

		Set<String> roleStringSet = new HashSet<String>();
		List<Role> roleList = roleMapper.getUserRoleList(userId);
		for (Role role : roleList) {
			roleStringSet.add(role.getCode());
		}
		return roleStringSet;
	}

	/**
	 * 判断当前的登录的用户是否是超级管理员
	 * @param user
	 * @return
	 */
	public boolean isSuperAdmin(User user){
		if(user != null) {
			Set<String> roles = getUserRoleStringSet(user.getId());
			if (roles.contains(Constants.ADMINCODE)) {
				return true;
			}
		}
		return false;
	}
}

