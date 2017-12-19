package com.github.service;

import com.github.mapper.PermissionMapper;
import com.github.model.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

	@Resource private PermissionMapper permissionMapper;

	public List<Permission> getUserMenuPermissionList(Integer userId) {

		List<Permission> permissionList = permissionMapper.getUserMenuPermissionList(userId);
		for (Permission permission : permissionList) {
			List<Permission> menuChildPermissionList = permissionMapper.getMenuChildPermissionList(permission.getId());
			permission.setPermissionList(menuChildPermissionList);
		}
		return permissionList;
	}
}

