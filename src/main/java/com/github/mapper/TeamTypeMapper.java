package com.github.mapper;

import com.github.model.TeamType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TeamTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamType record);

    int insertSelective(TeamType record);

    TeamType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamType record);

    int updateByPrimaryKey(TeamType record);

    List<TeamType> getList(TeamType record);

    int selectMaxSeqByPid(TeamType record);

    List<Map<String, Object>> getMap(TeamType record);
}