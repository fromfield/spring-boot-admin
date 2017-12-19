package com.github.controller;

import com.github.model.ResourceTag;
import com.github.service.ResourceTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("resourceTag")
public class ResourceTagController {

    @Autowired
    private ResourceTagService service;

    @ResponseBody
    @RequestMapping("add/{typeId}")
    public Object add(String ids,@PathVariable("typeId") Integer typeId) throws Exception {
        String []courseIds = StringUtils.split(ids,",");
        for(String id : courseIds) {
            ResourceTag tag = new ResourceTag();
            tag.setTagId(typeId);
            tag.setType(1);
            tag.setTypeId(Integer.parseInt(id));
            int count = service.insertSelective(tag);
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("delete/{typeId}")
    public Object delete(String ids,@PathVariable("typeId") Integer typeId) throws Exception {
        String []courseIds = StringUtils.split(ids,",");
        for(String id : courseIds) {
            ResourceTag tag = new ResourceTag();
            tag.setTagId(typeId);
            tag.setType(1);
            tag.setTypeId(Integer.parseInt(id));
            int count = service.deleteByCourseIdAndTypeId(tag);
        }
        return "";
    }

}
