package com.yaorange.myspringmvc.web.anontation;

import java.lang.annotation.*;

@Documented
//设置这个注解的声明周期
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD}) //表示这个注解可以用在类上和方法上
public @interface RequestMapping {
    String [] value();
}
