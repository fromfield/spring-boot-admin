package com.github.service;

import com.github.mapper.OrganizationExtendMapper;
import com.github.mapper.RoleMapper;
import com.github.model.OrganizationExtend;
import com.github.model.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrganizationExtendService {

	@Resource private OrganizationExtendMapper organizationExtendMapper;

	/**
	 * 获取所有机构
	 *  @author YangChengLiang
	 * @date 2017/11/8 11:36
	 * @return
	 */
	public List<OrganizationExtend> getList(OrganizationExtend organizationExtend) {
		return organizationExtendMapper.getList(organizationExtend);
	}

	/**
	 * 获取指定机构
	 * @author YangChengLiang
	 * @date 2017/11/8 11:36
	 * @param id
	 * @return
	 */
	public OrganizationExtend get(Integer id) {
		return organizationExtendMapper.selectByPrimaryKey(id);
	}
}

