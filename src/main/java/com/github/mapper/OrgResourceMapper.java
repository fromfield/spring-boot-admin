package com.github.mapper;

import com.github.model.OrgResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrgResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrgResource record);

    int insertSelective(OrgResource record);

    OrgResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrgResource record);

    int updateByPrimaryKey(OrgResource record);

    List<OrgResource> getList(OrgResource record);

}