package com.github.mapper;

import com.github.model.EcnuCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EcnuCourseMapper extends BaseMapper<EcnuCourse>{

    /**
     *
     * @param course
     * @return
     */
    List<EcnuCourse> selectByCondition(EcnuCourse course);

    int deleteBatch(List<Integer> ids);

    List<EcnuCourse> selectByTypeId(EcnuCourse course);

    List<EcnuCourse> selectByNoneTypeId(EcnuCourse course);

    List<EcnuCourse> selectHomeRecommend(EcnuCourse course);

    List<EcnuCourse> selectHomeNoneRecommend(EcnuCourse course);
}