package com.github.mapper;

import com.github.model.ResourceTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ResourceTagMapper extends BaseMapper<ResourceTag>{

    int deleteBatchByTypeIds(List<Integer> typeIds);

    int deleteByCourseIdAndTypeId(ResourceTag tag);
}