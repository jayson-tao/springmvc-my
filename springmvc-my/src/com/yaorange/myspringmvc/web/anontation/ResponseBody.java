package com.yaorange.myspringmvc.web.anontation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBody {
    }
