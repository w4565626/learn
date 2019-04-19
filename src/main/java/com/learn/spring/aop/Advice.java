package com.learn.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * SpringAop:动态代理，所谓的动态代理就是AOP框架不修改字节码，而是在内存中临时为方法生成一个对象AOP对象。
 * 这个AOP对象包含了目标对象的全部方法，并且在特定的切点做了增强处理，并回调原对象的方法。
 * 两种方式：
 * 1. JDK动态代理：通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口，核心是InvocationHandler接口和Proxy类,
 * 只能对实现了接口的类生成代理，而不能针对类
 * 2.CGLIB代理：如果目标类没有实现接口，那么SpringAOP会选择使用CGLIB来动态代理目标类。CGLIB(code generation library)是一个代码生成的类库，
 * 可以在运行时动态的生成某个类的子类，注意CGLIB是通过继承的方式做的动态代理，因为目标类如果标记为final，则无法代理。
 *
 * 和AspectJ对比：
 * AspectJ是静态代理，在编译时就增强了目标对象，生成字节码。springAop则是在每次运行时动态增强，生成AOP对象。
 * AspectJ具有更好的性能，但是需要特定的编译器处理，SpringAop无需特定编译器处理。
 */
@Aspect
@Component
@Slf4j
public class Advice {

    @Before("@annotation(timer)")
    public void before(Timer timer){
        System.out.println("before");
    }

//    @Around("@annotation(timer)")
//    public void pointcut(ProceedingJoinPoint point, Timer timer) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        assert attributes != null;
//        HttpServletRequest request = attributes.getRequest();
//        log.info("URL : " + request.getRequestURL().toString());
//        log.info("方法 : " + request.getMethod());
//        log.info("参数 : " + Arrays.toString(point.getArgs()));
//    }
}
