package com.github.mapper;

import com.github.model.Subject;
import com.github.model.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

    List<Team> getList(Team record);
    //未分类
    List<Team> selectTypeNotRTeam(@Param("typeId") Integer typeId, @Param("team") Team team);
    //分类中的小组
    List<Team> selectTypeRTeam(@Param("typeId") Integer typeId, @Param("team") Team team);
    //推荐小组
    List<Team> homePageRecommended(Team record);
    //未推荐小组
    List<Team> homePageNotRecommended(Team record);
}