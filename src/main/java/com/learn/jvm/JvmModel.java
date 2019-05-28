package com.learn.jvm;

/**
 * jvm内存模型
 * 1.程序计数器：线程独有，当前线程的行号指示器，cpu上下文切换的时候，会用到
 * 2.虚拟机栈：线程独有，每个方法的执行就是入栈到出栈的过程。存储了局部变量表、操作栈、动态链接、方法出口等。
 * 栈帧大小确定时间: 编译期确定，不受运行期数据影响
 * 3.本地方法栈：和虚拟机栈类似，只不过是虚拟机使用到的native方法服务。一般用不到
 * 4.堆：线程共享，存放对象实例，分为新生代老生代等，使用分代算法。后续详说。
 * 5.方法区：线程共享，存放被虚拟机加载的类信息、常量、静态变量等。java8称为元空间
 *
 */
public class JvmModel {
}