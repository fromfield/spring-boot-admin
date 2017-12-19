package com.github.mapper;

import com.github.model.FamousTeacherType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface FamousTeacherTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FamousTeacherType record);

    int insertSelective(FamousTeacherType record);

    FamousTeacherType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FamousTeacherType record);

    int updateByPrimaryKey(FamousTeacherType record);

    List<FamousTeacherType> getList(FamousTeacherType types);
    List<Map<String,Object>> getTree(Integer fid);
}