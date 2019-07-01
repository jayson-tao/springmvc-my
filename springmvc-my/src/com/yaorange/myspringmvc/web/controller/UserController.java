package com.yaorange.myspringmvc.web.controller;

import com.yaorange.myspringmvc.web.anontation.ResponseBody;
import com.yaorange.myspringmvc.web.entity.ModelAndView;
import com.yaorange.myspringmvc.web.anontation.Controller;
import com.yaorange.myspringmvc.web.anontation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("list");
        mv.addObject("user", "tom");
        return mv;
    }

    @RequestMapping("/list1")
    @ResponseBody
    public String list1() {
        String s = "responseBody";
        return s;
    }
}
