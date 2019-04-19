package com.learn.spring.aop;


import java.lang.annotation.*;

@Documented()
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {


}
