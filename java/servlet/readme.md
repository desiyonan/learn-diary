# 简介

## 认识Servlet

### Servlet 是什么

>Java Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。

#### Servlet 可以做什么

可以收集来自网页表单的用户输入，呈现来自数据库或者其他源的记录，还可以动态创建网页。

#### 特点

能够达到使用CGI（Common Gateway Interface，公共网关接口）的程序同样的效果，并且与之相比还具有的特点：

- 性能明显更好。
- Servlet 在 Web 服务器的地址空间内执行。这样它就没有必要再创建一个单独的进程来处理每个客户端请求。（单进程，多线程）
- Servlet 是独立于平台的，因为它们是用 Java 编写的。
- 服务器上的 Java 安全管理器执行了一系列限制，以保护服务器计算机上的资源。因此，Servlet 是可信的。
- Java 类库的全部功能对 Servlet 来说都是可用的。它可以通过 sockets 和 RMI 机制与 applets、数据库或其他软件进行交互。

### Servlet 架构

servlet 是处于 Web 服务器内的程序，它是一个 **中间层**，作为发送请求端和服务器端的数据库或应用程序之间的中间层

其架构处于：

![servlet 架构](./img/servlet-arch.jpg)

### Servlet 的作用

Servlet 在服务器上是用来接收和处理并返回数据结果的。

具体的任务包括：

- 读取客户端发送的数据
  - 显式数据：HTML 表单，或者自定义的数据表单
  - 隐式数据：cookies、媒体类型以及压缩格式文件
- 处理数据：访问数据库、调用其他服务等等
- 发送数据到客户端
  - 显式数据：即文档，一般 HTML，还包括文本文件，二进制文件等等
  - 隐式数据：这包括告诉浏览器或其他客户端被返回的文档类型（例如 HTML），设置 cookies 和缓存参数，以及其他类似的任务。

### Servlet 容器

>Servlet Container（Servlet 容器） 是 Web 服务器或者应用服务器的一部分，用于提供基于请求/响应发送模式的网络服务，解码基于 MIME 的请求，并且格式化基于 MIME 的响应。Servlet 容器同时也包含和管理他们的生命周期里 Servlet。

Servlet 容器也叫做 Servlet 引擎；并不等于 WEB 服务器，是 WEB 服务器的一部分。

Servlet 容器的分类：

1. 独立的 Servlet 容器

   基于 Java 技术的 Web 服务器，作为一部分存在。然而大多数 Web 服务器并非基于 Java，于是就有了下面两者。

2. 进程内的 Servlet 容器

   容器由两部分实现，Web 服务器插件和 Java 容器；Web 服务器插件在某个 Web 服务器的 **地址空间内** 打开一个内部 JVM 。

3. 进程外的 Servlet 容器

   也是由两个部分实现的，只是打开的 JVM 是位于 Web 服务器的 **地址空间外**,通常是使用IPC通信（TCP/IP)。

区分的方法也就是根据 Servlet 是否运行在 WEB 服务器上开辟的JVM空间，进程内的使用 JNI 机制，进程外的使用 IPC。

1. Servlet 容器是服务器的哪一部分？

   处理关于 Servlet 的请求\响应，容纳管理所有 Servlet ，是 Web 服务器和 Servlet 交互必不可少的组件。例如 tomcat 集成的 JSP 引擎 和 Servlet 引擎。

## 使用Servlet

Servlet 是运行在带 Java Servlet 规范的解释器上的 web 服务器上 的 Java 类。

在编写一个自定义的 Servlet 时，需要按照标准的 Java Servlet 规范进。

### 创建 Servlet

使用 `javax.servlet` 和 `javax.servlet.http` 包内定义的 java 类或者接口创建 Servlet

创建方式包括：

1. 实现 Servlet 接口
  
    ```java
    public class ServletDemo implements Servlet{}
    ```
    使用这种方式需要手动实现所有`Servlet`接口定义的方法，包括生命周期方法，但是灵活性更高

2. 继承已定义的 Servlet 类

    Java 提供两个已实现 Servlet 接口的类，`GenericServlet` 和 `HttpServlet`

    在开发网站应用时，我们通常都是直接继承的`HttpServlet`。

    ```java
    public class ServletDemo extends HttpServlet{}
    ```

### Servlet 生命周期

Servlet 的生命周期规定了如何被加载、实例化、初始化、处理请求，以及何时结束。可以通过 `javax.servlet.Servlet` 接口中的 `init`、`service`和`destroy` 这些 API 表示。JAVA 提供两个默认的 Servlet 接口实现，即`GenericServlet`和`HttpServlet`。

它们之间的区别在于：

- `GenericServlet`只实现了除 `service` 之外的方法
- `HttpServlet`是基于 http 实现的，它实现了 `service` 的同时还提供对应 http 方法的 `doGet`、`doPost`等。

执行顺序：

1. init

    初始化，当 Servlet 被容器加载实例化后对其调用

2. service

    业务处理，当服务器接受到客户端的请求时调用，执行具体业务逻辑。

3. destroy

    终止、销毁，当应用程序结束时调用，执行清理活动。

#### 初始化：init()

servlet 实例不是在服务器启动的时候就初始化，是在客户端请求的时候由 servlet 容器来加载生成实例对象，再调用 `init（）` 方法初始化。当然也可以在`web.xml`中使用`load-on-startup`来配置随服务器启动初始化指定的servlet。

初始化的目的在于方便让 Servlet 能读取持久化的配置，例如一些代价高的资源（JDBC数据库链接），或者执行一次性动作。

初始化方式：

对一个 Servlet 进行初始化都需要重写 `init()` 方法，并且还可以提供一个 **唯一** 的 ServletConfig 对象作为配置，

这个 ServletConfig 配置对象允许 Servlet 访问由 web 应用配置信息提供的键-值对的初始化参数，该配置对象也提供给Servlet 去访问一个 ServletContext 对象，ServletContext 描述了Servlet 的运行时环境。具体参考 "[Servlet 上下文](#1)"。

#### 请求处理： service()

servlet 完成初始化后，servlet 容器就可以使用它处理客户端请求了。

由 ServletRequest 类型的请求对象表示客户端请求，而返回的响应由 ServletResponse 类型的响应对象表示。这两个对象是由容器通过参数传递到 Servlet 接口的 `service` 方法的。

在 HTTP 请求的场景下，容器提供的请求和响应对象具体类型则被 **转化** 为HttpServletRequest 和 HttpServletResponse。

>需要注意的是，由 servlet 容器初始化的某个 servlet 实例在服务期间，可以在其生命周期中不处理任何请求。

在处理请求时还需要考虑的问题：

- 多线程处理
- 异常处理
- 异步处理
- 升级处理（HTTP/1.1）

#### 终止、销毁：destroy()

servlet 容器没必要保持装载的 servlet 持续任何特定的一段时间。一个 servlet 实例可能会在 servlet 容器内保持活跃（active）持续一段时间（以毫秒为单位），servlet容器的寿命可能是几天，几个月，或几年，或者是任何之间的时间。

当 servlet 容器确定 servlet 应该从服务中移除时，将调用 Servlet 接口的 destroy 方法以允许 servlet 释放它使用的任何资源和保存任何持久化的状态。例如，当想要节省内存资源或它被关闭时，容器可以做这个。

**调用条件：**  
在 servlet 容器调用 `destroy` 方法之前，它必须让当前正在执行 `service` 方法的任何线程完成执行，或者超过了服务器定义的时间限制。

一旦调用了 servlet 实例的 destroy 方法，容器无法再路由其他请求到该 servlet 实例了。如果容器需要再次使用该 servlet，它必须用该servlet 类的一个新的实例。在 destroy 方法完成后，servlet 容器必须释放 servlet 实例以便被垃圾回收。

问题：

1. 销毁 servlet 实例后，重新创建 servlet 是否会执行 `init`？

    会。

## Servlet 实现原理

## 参考

1. [Java Servlet 3.1 Specification《Java Servlet 3.1 规范》](https://waylau.gitbooks.io/servlet-3-1-specification/content/docs/The%20Servlet%20Interface/2.3%20Servlet%20Life%20Cycle.html)
