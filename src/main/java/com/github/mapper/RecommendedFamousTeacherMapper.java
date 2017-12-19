package com.github.mapper;

import com.github.model.RecommendedFamousTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface RecommendedFamousTeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendedFamousTeacher record);

    int insertSelective(RecommendedFamousTeacher record);

    RecommendedFamousTeacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendedFamousTeacher record);

    int updateByPrimaryKey(RecommendedFamousTeacher record);

    List<Map<String, Object>> getList(Map<String, Object> seachdata);

    List<Map<String, Object>> getNotInIndexList(Map<String, Object> seachdata);

}