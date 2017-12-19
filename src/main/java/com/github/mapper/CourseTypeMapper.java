package com.github.mapper;

import com.github.model.CourseType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseTypeMapper extends BaseMapper<CourseType> {

    /**
     * 可以传入类别名称（typeName）,父Id,status,机构Id
     * @param courseType
     * @return
     */
    List<CourseType> selectByCondition(CourseType courseType);

    int deleteBatch(List<Integer> ids);
}