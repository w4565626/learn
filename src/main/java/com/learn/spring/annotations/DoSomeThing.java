package com.learn.spring.annotations;

import java.util.Date;

public class DoSomeThing {
    @Annotations
    public static void test() {
        System.out.println("执行自定义注解");
        System.out.println("执行结束：" + new Date());
    }

}
