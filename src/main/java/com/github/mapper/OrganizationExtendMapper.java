package com.github.mapper;

import com.github.model.OrganizationExtend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrganizationExtendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationExtend record);

    int insertSelective(OrganizationExtend record);

    OrganizationExtend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrganizationExtend record);

    int updateByPrimaryKey(OrganizationExtend record);

    List<OrganizationExtend> getList(OrganizationExtend record);
}