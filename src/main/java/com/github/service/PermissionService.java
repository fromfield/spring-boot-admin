package com.github.service;

import com.github.mapper.PermissionMapper;
import com.github.model.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionService {

	@Resource private PermissionMapper permissionMapper;

	public Set<String> getUserPermissionStringSet(Integer userId) {

		Set<String> permissionStringSet = new HashSet<String>();
		Set<Permission> permissionSet = permissionMapper.getUserPermissionSet(userId);
		for (Permission permission : permissionSet) {
			permissionStringSet.add(permission.getCode());
		}
		return permissionStringSet;
	}
}

