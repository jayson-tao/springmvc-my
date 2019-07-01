package com.yaorange.myspringmvc.web.entity;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    //视图名
    private String viewName ;
    //模型数据
    private Map<String,Object> model = new HashMap<>();

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    /**
     *
     * @param key 模型数据的名
     * @param value  模型数据的值
     */
    public void addObject(String key, Object value) {
        model.put(key,value);
    }
}
