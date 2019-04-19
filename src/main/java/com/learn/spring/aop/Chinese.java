package com.learn.spring.aop;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 运行配置@Transactional注解的测试类的时候，具体会发生如下步骤
 * 1)事务开始时，通过AOP机制，生成一个代理connection对象，并将其放入DataSource实例的某个与DataSourceTransactionManager相关的某处容器中。
 * 在接下来的整个事务中，客户代码都应该使用该connection连接数据库，执行所有数据库命令[不使用该connection连接数据库执行的数据库命令，
 * 在本事务回滚的时候得不到回滚]
 * 2)事务结束时，回滚在第1步骤中得到的代理connection对象上执行的数据库命令，然后关闭该代理connection对象
 *
 */
@Component
@Transactional
public class Chinese implements Person {

    @Timer
    public String sayHello(String name) {
        System.out.println("--sayHello()--");
        return name + ":hello";
    }
}
