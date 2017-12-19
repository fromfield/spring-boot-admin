package com.github.mapper;

import com.github.model.FamousTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface FamousTeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FamousTeacher record);

    int insertSelective(FamousTeacher record);

    FamousTeacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FamousTeacher record);

    int updateByPrimaryKey(FamousTeacher record);

    List<FamousTeacher> getList(FamousTeacher famousTeacher);


    List<FamousTeacher> getListNotInType(Map<String,Object> seacherdata);
}