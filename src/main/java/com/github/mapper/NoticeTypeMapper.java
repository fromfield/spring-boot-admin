package com.github.mapper;

import com.github.model.NoticeType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface NoticeTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeType record);

    int insertSelective(NoticeType record);

    NoticeType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeType record);

    int updateByPrimaryKey(NoticeType record);

    List<Map<String, Object>> getList(NoticeType type);
}