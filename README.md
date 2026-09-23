# 课后练习10 —— 单例模式的六种写法（singleton_test）

> 实验指导中几种单例模式（6 种）。单例模式 = 全局只有一个实例——Spring 容器里的 bean 默认就是 `singleton` 作用域，这六个类就是"Spring 帮你做的事"的手工底层版。
> 纯 Java 工程 · JUnit 4.13.1 · JDK 17 · 包名 `lwy.study.singleton`

## 项目结构

```
src/main/java/lwy/study/singleton/
├── Singleton.java    ①饿汉式：声明字段时直接 new
├── Singleton2.java   ②饿汉式：静态代码块里 new
├── Singleton3.java   ③懒汉式：第一次调用才 new（多线程不安全）
├── Singleton4.java   ④懒汉式：方法加 synchronized（安全但每次抢锁）
├── Singleton5.java   ⑤双重检查锁 DCL：两次判空 + synchronized 块
└── Singleton6.java   ⑥静态内部类：借 JVM 类加载机制，推荐
src/test/java/lwy/study/singleton/SingletonTest.java   六个测试：两次 getInstance 必须 assertSame
```

## 六种写法速记

| # | 写法 | 核心 | 线程安全 |
|---|---|---|---|
| ① | 饿汉式 | `private static Singleton instance = new Singleton();` | ✅ 类加载时创建 |
| ② | 饿汉式·静态块 | 初始化逻辑放进 `static {}` | ✅ 同上 |
| ③ | 懒汉式 | `if (instance == null) instance = new ...` | ❌ 多线程会 new 出多个 |
| ④ | 懒汉式·同步方法 | `public static synchronized getInstance()` | ✅ 但锁整方法，效率低 |
| ⑤ | 双重检查锁 DCL | 外层判空 → `synchronized(Singleton.class)` → 内层再判空 | ✅ 兼顾效率 |
| ⑥ | 静态内部类 | `private static class Holder { static final INSTANCE = new ... }` | ✅ 推荐 |

共同点：**构造方法全部 private**——不给外界 new 的机会，只能走 `getInstance()`。

## 抄作业时你可能遇到的坑（都是真踩过的）

1. **不能 `new Singleton()`**：构造方法是 private 的，IDEA 会直接报错——这不是写错，单例就是这个设计，要拿对象只能 `Singleton.getInstance()`
2. **junit 版本**：Maven 骨架默认送的是 junit **3.8.1**（上古版本），`assertSame` 都没有——手动改成 **4.13.1**
3. **IDEA 一片红 / 测试跑不起来**：外部改过代码后 IDEA 缓存是旧的 → `File → Reload All from Disk` → `Build → Rebuild Project`，红线基本消失
4. **测试输出"全是 null"别慌**：`TestTeacherFactory` 那种工厂类测试输出 null 字段是**正常的**（工厂只造对象不赋值）；单例测试看的是 **exit code 0** 和绿色通过条，不是看字段值
5. **lombok 项目**记得开 `Settings → Build → Compiler → Annotation Processors → Enable`（本工程没用 lombok，不受影响）
6. **抄别人的工程先改包名**：`lq` → `lwy`（你的名字缩写），**目录和每个文件顶部的 package 行都要改**，漏一个地方编译就报错
7. **懒汉式③单独记住**：考试爱问"它线程为什么不安全"——两个线程同时过 `if (instance == null)`，会各自 new 一个，单例就被破坏了；④⑤就是给它打的补丁

## 运行方式

1. IDEA 打开项目（Open 选 pom.xml），Maven 同步
2. 跑 `SingletonTest`：6 个测试全绿 = 六种写法全部单例成功
