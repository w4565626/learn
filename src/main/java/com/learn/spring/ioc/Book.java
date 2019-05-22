package com.learn.spring.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class Book implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {
    private String bookName;

    public Book() {
        System.out.println("Book Initializing ");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Book.setBeanFactory invoke");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("Book.setBeanName invoke");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Book.destory invoke");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Book.afterPropertiesSet invoke");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("Book.setApplicationContext invoke");
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
        System.out.println("setBookName: Book name has set.");
    }

    public void myPostConstruct() {
        System.out.println("Book.myPostConstruct invoke");
    }

    // 自定义初始化方法
    @PostConstruct
    public void springPostConstruct() {
        System.out.println("@PostConstruct");
    }

    public void myPreDestory() {
        System.out.println("Book.myPreDestory invoke");
        System.out.println("---------------destroy-----------------");
    }

    // 自定义销毁方法
    @PreDestroy
    public void springPreDestory() {
        System.out.println("@PreDestory");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("------inside finalize-----");
    }
}
