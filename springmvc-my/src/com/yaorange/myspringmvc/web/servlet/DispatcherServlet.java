package com.yaorange.myspringmvc.web.servlet;


import com.yaorange.myspringmvc.web.anontation.Controller;
import com.yaorange.myspringmvc.web.anontation.RequestMapping;
import com.yaorange.myspringmvc.web.entity.HandlerAdapter;
import com.yaorange.myspringmvc.web.entity.ModelAndView;
import com.yaorange.myspringmvc.web.entity.View;
import com.yaorange.myspringmvc.web.entity.ViewResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前端控制器
 */
public class DispatcherServlet extends HttpServlet {
    //定义handlermapping key--uri value--method
    private Map<String, Method> handlerMapping = new HashMap<>();
    //controller 方法和类的映射
    private Map<Method, Object> controllerMapping = new HashMap<>();
    //适配器
    private HandlerAdapter handlerAdapter = new HandlerAdapter();
    //视图解析器
    private ViewResolver viewResolver = new ViewResolver();
    //扫描包的路径
    private String scanPath = "com.yaorange.myspringmvc.web.controller";
    private List<String> list = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        scanPacket(scanPath);
        initHandlerMapping();
    }

    /**
     * 初始化handlermapping
     */
    private void initHandlerMapping() {
        //遍历类名集合找到注解并初始化handlermapping
        for (String s : list) {
            try {
                Class<?> clzz = Class.forName(s);
                //如果有controller注解
                if (clzz.isAnnotationPresent(Controller.class)) {
                    //创建实例
                    Object controller = clzz.newInstance();
                    //获取所有方法public
                    Method[] method = clzz.getMethods();
                    RequestMapping annotation = clzz.getAnnotation(RequestMapping.class);
                    String uri = "";
                    if (annotation != null) {
                        //如果类上有注解获取第一个参数
                        uri = annotation.value()[0];
                    }
                    //遍历方法
                    for (Method method1 : method) {
                        if (method1.isAnnotationPresent(RequestMapping.class)) {
                            //如果方法上有requestmapping就拼接上uri
                            uri += method1.getAnnotation(RequestMapping.class).value()[0];
                            handlerMapping.put(uri, method1);
                            controllerMapping.put(method1, controller);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 扫描包下所有文件
     *
     * @param scanPath
     */
    private void scanPacket(String scanPath) {
        //获取类路径
        URL url = this.getClass().getClassLoader().getResource(scanPath.replaceAll("\\.", "/"));
        File file = new File(url.getPath());
        if (file != null) {
            //如果文件不为空就遍历所有的文件
            File[] files = file.listFiles();
            for (File file1 : files) {
                //如果是文件夹就递归遍历
                if (file1.isDirectory()) {
                    //加上文件夹的名字
                    scanPacket(scanPath + "." + file1.getName());
                } else {
                    //存下类名
                    list.add(scanPath+"."+file1.getName().replaceAll(".class", ""));
                }
            }
        }
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求uri
        String uri = req.getRequestURI();
        //根据handlermapping查找handler
        Method method = handlerMapping.get(uri);
        //根据controllermapping找到method 对应的controller
        Object controller = controllerMapping.get(method);
        //说明资源映射正确 调用适配器处理方法
        if (method != null) {
            // 适配器器处理handler 返回modelandview
            ModelAndView mv = handlerAdapter.invok(method, controller, req, resp);
            // 视图解析器解析modelandview解析视图--返回view
            View view = viewResolver.resolveView(mv);
            //view--拿到模型数据渲染页面--响应浏览器
            view.render(mv.getModel(), req, resp);
        } else {
            //表示请求的资源不存在
            req.setAttribute("msg", "404");
            req.getRequestDispatcher("erro.jsp").forward(req, resp);
        }
    }

}
