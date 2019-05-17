package com.learn.java8;

/**
 * 强引用：只要引用存在，就永远不会回收
 * Object obj = new Object();
 *
 * 弱引用：第二次垃圾回收时回收
 * Object obj = new Object();
 * WeakReference<Object> wf = new WeakReference<Object>(obj);
 * obj = null;
 * wf.get();//有时候会返回null
 * wf.isEnQueued();//返回是否被垃圾回收器标记为即将回收的垃圾
 * 弱引用是在第二次垃圾回收时回收，短时间内通过弱引用取对应的数据，可以取到，当执行过第二次垃圾回收时，将返回null。
 * 弱引用主要用于监控对象是否已经被垃圾回收器标记为即将回收的垃圾，可以通过弱引用的isEnQueued方法返回对象是否被垃圾回收器标记。
 *
 * 软引用：
 * 非必需引用，内存溢出之前回收
 * Object obj = new Object();
 * SoftReference<Object> sf = new SoftReference<Object>(obj);
 * obj = null;
 * sf.get();//有时候会返回null
 * 这时候sf是对obj的一个软引用，通过sf.get()方法可以取到这个对象，当然，当这个对象被标记为需要回收的对象时，则返回null；
 * 软引用主要用户实现类似缓存的功能，在内存足够的情况下直接通过软引用取值，无需从繁忙的真实来源查询数据，提升速度；
 * 当内存不足时，自动删除这部分缓存数据，从真正的来源查询这些数据。
 *
 * 虚引用：
 * 垃圾回收时回收，无法通过引用取到对象值
 * bject obj = new Object();
 * PhantomReference<Object> pf = new PhantomReference<Object>(obj);
 * obj=null;
 * pf.get();//永远返回null
 * pf.isEnQueued();//返回是否从内存中已经删除
 * 虚引用是每次垃圾回收的时候都会被回收，通过虚引用的get方法永远获取到的数据为null，因此也被成为幽灵引用。
 * 虚引用主要用于检测对象是否已经从内存中删除。
 *
 */
public class Reference {
}
