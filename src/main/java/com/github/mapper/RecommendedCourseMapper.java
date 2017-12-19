package com.github.mapper;

import com.github.model.RecommendedCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RecommendedCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendedCourse record);

    int insertSelective(RecommendedCourse record);

    RecommendedCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendedCourse record);

    int updateByPrimaryKey(RecommendedCourse record);

    int deleteByFidAndCourseId(@Param("fid") Integer fid, @Param("courseId") Integer courseId);
}