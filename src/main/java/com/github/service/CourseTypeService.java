package com.github.service;

import com.github.mapper.BaseMapper;
import com.github.mapper.CourseTypeMapper;
import com.github.mapper.ResourceTagMapper;
import com.github.model.CourseType;
import com.github.pagehelper.PageHelper;
import com.github.util.Constants;
import com.github.util.CourseFidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseTypeService extends BaseService<CourseType>{

    @Autowired
    private CourseTypeMapper dao;

    @Autowired
    private ResourceTagMapper resourceTagDao;

    /**
     * 查找顶级类别
     * @param courseType
     * @param page
     * @param pageSize
     * @return
     */
    public List<CourseType> selectTopType(CourseType courseType,Integer page,Integer pageSize){
         if(courseType == null){
             courseType = new CourseType();
         }
        courseType.setParentId(Constants.COURSE_TYPE_TOP_PARENT_ID);
        return selectTypeByCondition(courseType,page,pageSize);
    }

    /**
     * 根据ParentId查找子级类别
     * @param courseType
     * @param parentId
     * @param page
     * @param pageSize
     * @return
     */
    public List<CourseType> selectTypeByParentId(CourseType courseType,Integer parentId,Integer page,Integer pageSize){
        if(courseType == null){
            courseType = new CourseType();
        }
        courseType.setParentId(parentId);
        return selectTypeByCondition(courseType,page,pageSize);
    }

    public List<CourseType> selectTypeByCondition(CourseType courseType,Integer page,Integer pageSize){
        if(courseType != null){
            Integer fid = CourseFidUtil.getFid();
            courseType.setFid(fid);
            courseType.setStatus(Constants.COURSE_TYPE_ENABLE_STATUS);
            PageHelper.startPage(page,pageSize);
            return dao.selectByCondition(courseType);
        }
        return null;
    }

    /**
     * 为树形结构提供查询，查询全部
     * @return
     */
    public List<CourseType> selectAllType() {

        CourseType courseType = new CourseType();
        Integer page = 1;
        Integer pageSize = Integer.MAX_VALUE;
        courseType.setParentId(Constants.COURSE_TYPE_TOP_PARENT_ID);
        List<CourseType> parents = selectTypeByCondition(courseType, page, pageSize);
        if (parents != null) {
            for (int index = 0, length = parents.size(); index < length; index++) {
                courseType.setParentId(parents.get(index).getId());
                List<CourseType> children = selectTypeByCondition(courseType, page, pageSize);
                parents.get(index).setChildren(children);
            }
        }
        return parents;
    }

    /**
     * 删除类型，包括其子级类型，Resource_tag表中的关联数据
     * @param id
     * @return
     */
    @Transactional
    public int delete(Integer id){
        if(id != null) {
            List<Integer> deletes = new ArrayList<>();
            deletes.add(id);
            CourseType courseType = new CourseType();
            List<CourseType> courseTypes = selectTypeByParentId(courseType, id, 1, Integer.MAX_VALUE);
            if (courseTypes != null) {
                for (int index=0,length=courseTypes.size();index<length;index++){
                    deletes.add(courseTypes.get(index).getId());
                }
            }
            int count = resourceTagDao.deleteBatchByTypeIds(deletes);
            count = dao.deleteBatch(deletes);
            return count;
        }
        return 0;
    }


    public int add(CourseType courseType){
        if(courseType != null){
            Integer fid = CourseFidUtil.getFid();
            courseType.setFid(fid);
            courseType.setStatus(Constants.COURSE_TYPE_ENABLE_STATUS);
            return dao.insertSelective(courseType);
        }
        return 0;
    }

    @Override
    protected BaseMapper getBaseDao() {
        return dao;
    }
}
