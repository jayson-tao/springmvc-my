package com.yaorange.myspringmvc.web.entity;

/**
 * 视图解析器
 */
public class ViewResolver {
    //前缀
    private String prefix = "/user/";
    //后缀
    private String subfix = ".jsp";

    public View resolveView(ModelAndView mv) {
        //得到视图名
        String viewName = mv.getViewName();
        //得到真正视图的
        String view = prefix+viewName+subfix;
        //返回一个视图
        return  new View(view);
    }
}
