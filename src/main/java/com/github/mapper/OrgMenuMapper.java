package com.github.mapper;

import com.github.model.OrgMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrgMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrgMenu record);

    int insertSelective(OrgMenu record);

    OrgMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrgMenu record);

    int updateByPrimaryKey(OrgMenu record);

    List<OrgMenu> getList(OrgMenu record);
}