package com.learn.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
    private Object object;

    public JDKProxy(Object object) {
        this.object = object;
    }

    public Object bind() {
        return (Object) Proxy.
                newProxyInstance(object.getClass().getClassLoader(),
                        object.getClass().getInterfaces(),
                        new JDKProxy(object));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(object, args);
    }
}
