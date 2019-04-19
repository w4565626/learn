package com.learn.test;

import com.learn.spring.aop.CGLIBProxy;
import com.learn.spring.aop.Chinese;
import com.learn.spring.aop.JDKProxy;
import com.learn.spring.aop.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdviceTest {
    @Autowired
    private Chinese chinese;

    @Test
    public void test() {
        chinese.sayHello("listen");
        System.out.println(chinese.getClass());
    }

    @Test
    public void testCGLIB(){
        Chinese chinese=new Chinese();
        CGLIBProxy proxy=new CGLIBProxy(chinese);
        Chinese bind = (Chinese) proxy.bind();
        bind.sayHello("listen");
        System.out.println(bind.getClass());
    }

    @Test
    public void testJDKProxy() {
        Person chinese = new Chinese();
        JDKProxy proxy = new JDKProxy(chinese);
        Person chineseProxy = (Person) proxy.bind();
        chineseProxy.sayHello("listen");
        System.out.println(chineseProxy.getClass());
    }
}
