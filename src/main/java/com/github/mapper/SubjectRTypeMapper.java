package com.github.mapper;

import com.github.model.SubjectRType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SubjectRTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SubjectRType record);

    int insertSelective(SubjectRType record);

    SubjectRType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SubjectRType record);

    int updateByPrimaryKey(SubjectRType record);

    int deleteByTypeIdAndSubjectId(SubjectRType record);

    int deleteByTypeId(Integer typeId);
}