package com.github.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.util.Constants;
import com.github.util.HttpRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ApiService {

    /**
     * 通过泛雅id查询泛雅课程信息（专题）
     * @author YangChengLiang
     * @date 2017/11/10 13:55
     * @param courseId
     * @return
     * @throws Exception
     */
    public JSONObject getFanyaCourseId(Integer courseId) {
        JSONObject jsonObject=null;
        try {
            if(courseId != null) {
                HttpRequest httpRequest = new HttpRequest();
                String result = "";
                String url = Constants.GRT_COURSE_DETAIL_HREF.replace("{ids}", courseId.toString());
                result = httpRequest.doGet(url);
                JSONArray jsonArray = JSONObject.parseObject(result).getJSONArray("data");
                if(jsonArray.size()>0){
                    jsonObject = jsonArray.getJSONObject(0);
                }
            }
        }catch ( Exception e){
            System.err.print("getFanyaCourseId:");
            System.err.println(e);
        }
        return  jsonObject;
    }
}
