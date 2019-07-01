package com.yaorange.myspringmvc.web.entity;

import com.yaorange.myspringmvc.web.anontation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 适配器器
 */
public class HandlerAdapter {
    /**
     * 通过反射调用handler
     * @param method 控制器中的handler
     * @param controller 控制器实例
     * @return
     */
    public ModelAndView invok(Method method, Object controller, HttpServletRequest req, HttpServletResponse resp) {
       //获取handler中的参数个数
        int count = method.getParameterCount();
        //获去handler中的参数列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        //定义参数数组
        Object [] params = new Object[count];
        //判断参数列表
        for (int i = 0; i <params.length ; i++) {
            //判断参数类型
            if(parameterTypes[i].getName().equals(HttpServletRequest.class.getName())){
                params[i] = HttpServletRequest.class;
            }
        }
        //执行handler
        ModelAndView mv = new ModelAndView();
        try {
            Object resoult = method.invoke(controller, params);
            if(resoult instanceof ModelAndView){
               mv = (ModelAndView) resoult;
            }else if(resoult instanceof String){
                //如果返回的是string
                mv.setViewName((String) resoult);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return mv;
    }
}
