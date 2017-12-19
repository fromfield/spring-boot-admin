package com.github.mapper;

import com.github.model.RecommendedTeam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RecommendedTeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendedTeam record);

    int insertSelective(RecommendedTeam record);

    RecommendedTeam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendedTeam record);

    int updateByPrimaryKey(RecommendedTeam record);

    int deleteByFidAndTeamId(RecommendedTeam record);
}