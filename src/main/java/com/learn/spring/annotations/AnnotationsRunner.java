package com.learn.spring.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class AnnotationsRunner {
    public static void main(String[] args) {
        execute(DoSomeThing.class);
    }

    private static void execute(Class clazz) {
        System.out.println("开始执行：" + new Date());
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotations annotation = method.getAnnotation(Annotations.class);
            if (annotation!=null) {
                try {
                    System.out.println("");
                    method.invoke(clazz.newInstance(), null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
