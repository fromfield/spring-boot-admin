package com.github.mapper;

import com.github.model.RecommendedNotice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RecommendedNoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendedNotice record);

    int insertSelective(RecommendedNotice record);

    RecommendedNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendedNotice record);

    int updateByPrimaryKey(RecommendedNotice record);
}