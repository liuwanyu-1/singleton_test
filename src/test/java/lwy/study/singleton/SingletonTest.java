package lwy.study.singleton;

import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * 六种单例写法验证：多次 getInstance 拿到的必须是同一个对象
 */
public class SingletonTest {

    @Test
    public void testHungry() {
        // 饿汉式：类加载时就 new 好，天然线程安全
        assertSame(Singleton.getInstance(), Singleton.getInstance());
    }

    @Test
    public void testHungryBlock() {
        // 饿汉式（静态代码块）：效果同上，初始化逻辑挪到 static {} 里
        assertSame(Singleton2.getInstance(), Singleton2.getInstance());
    }

    @Test
    public void testLazy() {
        // 懒汉式：第一次调用才创建（单线程下单例成立，多线程会失效）
        assertSame(Singleton3.getInstance(), Singleton3.getInstance());
    }

    @Test
    public void testLazySync() {
        // 懒汉式加 synchronized：线程安全，但每次都要抢锁
        assertSame(Singleton4.getInstance(), Singleton4.getInstance());
    }

    @Test
    public void testDCL() {
        // 双重检查锁：两次判空 + 锁，兼顾线程安全与效率
        assertSame(Singleton5.getInstance(), Singleton5.getInstance());
    }

    @Test
    public void testInnerClass() {
        // 静态内部类：靠 JVM 类加载机制保证单例，推荐写法
        assertSame(Singleton6.getInstance(), Singleton6.getInstance());
    }
}
