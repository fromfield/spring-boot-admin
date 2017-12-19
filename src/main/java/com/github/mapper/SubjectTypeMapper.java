package com.github.mapper;

import com.github.model.SubjectType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SubjectTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SubjectType record);

    int insertSelective(SubjectType record);

    SubjectType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SubjectType record);

    int updateByPrimaryKey(SubjectType record);

    int selectMaxSeqByPid(SubjectType record);

    List<SubjectType> getList(SubjectType record);

    List<Map<String, Object>> getMap(SubjectType record);
}