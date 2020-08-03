package com.fh.common;

import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)  //用于描述注解的使用范围 用在方法上面
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAnnotation {
}
