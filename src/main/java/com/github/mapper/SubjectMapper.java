package com.github.mapper;

import com.github.model.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Subject record);

    int insertSelective(Subject record);

    Subject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Subject record);

    int updateByPrimaryKey(Subject record);

    List<Subject> selectTypeRSubject(@Param("typeId") Integer typeId, @Param("subject") Subject subject);

    List<Subject> selectTypeNotRSubject(@Param("typeId") Integer typeId, @Param("subject") Subject subject);

    List<Subject> getList(Subject record);

    List<Subject> homePageRecommended(Subject record);
    List<Subject> homePageNotRecommended(Subject record);
}