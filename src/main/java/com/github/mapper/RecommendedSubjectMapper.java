package com.github.mapper;

import com.github.model.RecommendedSubject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RecommendedSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendedSubject record);

    int insertSelective(RecommendedSubject record);

    RecommendedSubject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendedSubject record);

    int updateByPrimaryKey(RecommendedSubject record);

    int deleteByFidAndSubjectId(RecommendedSubject record);
}