package com.github.mapper;

import com.github.model.TeamRType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeamRTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamRType record);

    int insertSelective(TeamRType record);

    TeamRType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamRType record);

    int updateByPrimaryKey(TeamRType record);

    int deleteByTypeId(Integer typeId);

    int deleteByTypeIdAndTeamId(TeamRType record);
}