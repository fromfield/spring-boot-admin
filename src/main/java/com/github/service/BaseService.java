package com.github.service;

import com.github.mapper.BaseMapper;

public abstract class BaseService<T> implements IBaseService<T>{
    @Override
    public int deleteByPrimaryKey(Integer id) {
        if(id != null){
            return  getBaseDao().deleteByPrimaryKey(id);
        }
        return 0;
    }

    @Override
    public int insert(T record) {
        if(record != null){
            return getBaseDao().insert(record);
        }
        return 0;
    }

    @Override
    public int insertSelective(T record) {
        if(record != null){
            return getBaseDao().insertSelective(record);
        }
        return 0;
    }

    @Override
    public T selectByPrimaryKey(Integer id) {
        if(id != null){
            return getBaseDao().selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        if(record != null){
            return getBaseDao().updateByPrimaryKeySelective(record);
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKey(T record) {
        if(record != null){
            return getBaseDao().updateByPrimaryKey(record);
        }
        return 0;
    }

    protected abstract BaseMapper<T> getBaseDao();
}
