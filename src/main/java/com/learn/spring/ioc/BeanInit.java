package com.learn.spring.ioc;

import com.learn.spring.aop.Chinese;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Bean的生命周期：
 * 1. spring启动，查找并加载需要被spring管理的bean，进行bean的实例化
 * 2. 将bean的引入和值注入到bean的属性中
 * 3. 如果bean实现了{@link org.springframework.beans.factory.BeanNameAware} ,Spring将Bean的id传递给setBeanName方法
 * 4. 如果bean实现了{@link org.springframework.beans.factory.BeanFactoryAware} ,spring调用setBeanFactory方法，将beanfactory容器实例传入
 * 5. 如果bean实现了{@link org.springframework.context.ApplicationContextAware} ,Spring调用setApplicationContext()方法，将bean所在应用上下文引用传入
 * 6. 如果bean实现了{@link org.springframework.beans.factory.config.BeanPostProcessor} ,spring调用postProcessBeforeInitialization方法
 * 7. 如果bean实现了{@link org.springframework.beans.factory.InitializingBean} ,Spring将调用他们的afterPropertiesSet()方法。类似的，
 * 如果bean使用init-method声明了初始化方法，该方法也会被调用
 * 8. 如果Bean 实现了BeanPostProcessor接口，Spring就将调用他们的postProcessAfterInitialization()方法。
 * 9. bean准备就绪，存在应用上下文中。
 * 10. 如果bean实现了DisposableBean接口，Spring将调用它的destory()接口方法，同样，
 * 如果bean使用了destory-method 声明销毁方法，该方法也会被调用。
 */
public class BeanInit {

}
