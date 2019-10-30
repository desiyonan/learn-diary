# Reference 源码解读

## Table of Contents

- [Reference 源码解读](#reference-%e6%ba%90%e7%a0%81%e8%a7%a3%e8%af%bb)
  - [Table of Contents](#table-of-contents)
  - [引言](#%e5%bc%95%e8%a8%80)
  - [Reference 是什么](#reference-%e6%98%af%e4%bb%80%e4%b9%88)
  - [分类](#%e5%88%86%e7%b1%bb)
  - [相关文章资料](#%e7%9b%b8%e5%85%b3%e6%96%87%e7%ab%a0%e8%b5%84%e6%96%99)

## 引言

最近闲下来学习kryo源码时刚好看java的引用相关用法，印象已经模糊，还是重新记录下，主要还是针对`JDK8`而言，文中如有不对请邮件或QQ联系：
QQ： 1310332521
邮箱：1310332521@qq.com

## Reference 是什么

`Reference` 作及物动词时意为“引用”，在JAVA语言中通常用来代表一个对象实例, 又被称作引用变量、变量；最常见的代码 `String msg = new String("Hello World!")`，大致可分为三步：

1. 声明一个叫 `msg` 的引用变量
2. 创建一个内容 `Hello World!` 字符串对象实例
3. 将 `Hello World!` 该字符串对象的内存地址给 `msg` 引用变量

然后就能通过 `msg` 来对该字符串进行各种处理，通俗易懂的说就是给生成的对象实例的名称

## 分类

基类：`java.lang.ref.Reference`
实现：

- 强引用(`StrongReference`)
- 软引用(`java.lang.ref.SoftReference`)
- 弱引用(`java.lang.ref.WeakReference`)
- 虚引用(`java.lang.ref.PhantomReference`)

## 相关文章资料

[JDK源码阅读-Reference](http://imushan.com/2018/08/19/java/language/JDK%E6%BA%90%E7%A0%81%E9%98%85%E8%AF%BB-Reference/)
[Java篇 - 四种引用(Reference)实战](https://blog.csdn.net/u014294681/article/details/86511451)
