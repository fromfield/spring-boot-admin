package com.github.service;

import com.github.mapper.BaseMapper;
import com.github.mapper.ResourceTagMapper;
import com.github.model.ResourceTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceTagService extends BaseService<ResourceTag>{

    @Autowired
    private ResourceTagMapper dao;

    @Override
    protected BaseMapper getBaseDao() {
        return dao;
    }

    public int deleteByCourseIdAndTypeId(ResourceTag tag) {

        return dao.deleteByCourseIdAndTypeId(tag);
    }
}
