package com.yaorange.myspringmvc.web.entity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 视图---给视图凭借前后缀 、渲染模型数据
 */
public class View {
    //视图名
    private String view;

    public View(String view) {
        this.view = view;
    }

    public View() {
    }

    /**
     * 渲染页面
     *
     * @param model 模型数据
     */
    public void render(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置模型数据
        for (String s : model.keySet()) {
            req.setAttribute(s, model.get(s));
        }
        //把view响应给浏览器
        req.getRequestDispatcher(view).forward(req, resp);
    }
}
