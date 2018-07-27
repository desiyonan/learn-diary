# HTTP

## 简介

HTTP协议是Hyper Text Transfer Protocol（超文本传输协议）的缩写,是用于从万维网（WWW:World Wide Web ）服务器传输超文本到本地浏览器的传送协议。。

HTTP是一个基于TCP/IP通信协议来传递数据（HTML 文件, 图片文件, 查询结果等）。

## 工作原理

HTTP协议工作于客户端-服务端架构上。浏览器作为HTTP客户端通过URL向HTTP服务端即WEB服务器发送所有请求。

Web服务器有：Apache服务器，IIS服务器（Internet Information Services）等。

Web服务器根据接收到的请求后，向客户端发送响应信息。

HTTP默认端口号为80，但是你也可以改为8080或者其他端口。

HTTP三点注意事项：

- HTTP是无连接：无连接的含义是限制每次连接只处理一个请求。服务器处理完客户的请求，并收到客户的应答后，即断开连接。采用这种方式可以节省传输时间。
- HTTP是媒体独立的：这意味着，只要客户端和服务器知道如何处理的数据内容，任何类型的数据都可以通过HTTP发送。客户端以及服务器指定使用适合的MIME-type内容类型。
- HTTP是无状态：HTTP协议是无状态协议。无状态是指协议对于事务处理没有记忆能力。缺少状态意味着如果后续处理需要前面的信息，则它必须重传，这样可能导致每次连接传送的数据量增大。另一方面，在服务器不需要先前信息时它的应答就较快。

以下图表展示了HTTP协议通信流程：

![HTTP协议通信流程](./img/http_workflow.gif)

## 消息结构

HTTP是基于客户端/服务端（C/S）的架构模型，通过一个可靠的链接来交换信息，是一个无状态的请求/响应协议。

一个HTTP"客户端"是一个应用程序（Web浏览器或其他任何客户端），通过连接到服务器达到向服务器发送一个或多个HTTP的请求的目的。

一个HTTP"服务器"同样也是一个应用程序（通常是一个Web服务，如Apache Web服务器或IIS服务器等），通过接收客户端的请求并向客户端发送HTTP响应数据。

HTTP使用统一资源标识符（Uniform Resource Identifiers, URI）来传输数据和建立连接。

一旦建立连接后，数据消息就通过类似Internet邮件所使用的格式[RFC5322]和多用途Internet邮件扩展（MIME）[RFC2045]来传送。

### 客户端请求消息

客户端发送一个HTTP请求到服务器的请求消息包括以下格式：请求行（request line）、请求头部（header）、空行和请求数据四个部分组成，下图给出了请求报文的一般格式。

![请求消息结构](./img/http_request.png)

实例：

```http
GET /LearnDiary/test/request/?name=s&path=f&mt=get HTTP/1.1
host: localhost:8080
connection: keep-alive
upgrade-insecure-requests: 1
user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36
accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
referer: http://localhost:8080/LearnDiary/
accept-encoding: gzip, deflate, br
accept-language: zh-CN,zh;q=0.9
cookie: JSESSIONID=5813C905032830A997D5E556D73E10ED
```

#### 请求方法

HTTP1.0定义了三种请求方法： GET, POST 和 HEAD方法。

HTTP1.1新增了五种请求方法：OPTIONS, PUT, DELETE, TRACE 和 CONNECT 方法。

| method  | description                         | feature                               |
| ------- | ----------------------------------- | ------------------------------------- |
| GET     | 获取资源并返回                      | 只有header ，没有body                 |
| POST    | 提交数据，用于C/U                   | 响应头带有重定向指令 Content-Location |
| PUT     | 传输文件，用于U                     |                                       |
| DELETE  | 删除文件，用于D                     | 只有header，没有body。                |
| HEAD    | 获得报文头部                        | 类似 get ，但是没有主体部分           |
| CONNECT | 用隧道协议链接代理                  | SSL 和 TLS 加密                       |
| OPTIONS | 询问支持的方法，查看服务性能        |                                       |
| TRACE   | 追踪路径， 回显请求，用于测试和诊断 | 响应：body中包含整个请求消息。        |

#### 请求头

| name                | description                                                               |
| ------------------- | ------------------------------------------------------------------------- |
| Accept              | 接受的内容类型                                                            |
| Accept-Charset      | 接受的字符编码                                                            |
| Accept-Encoding     | 接受的编码格式                                                            |
| Accept-Dateime      | 接受的版本时间                                                            |
| Accept-Language     | 接受的语言                                                                |
| Authorization       | Http 身份验证凭证                                                         |
| Cache-Control       | 设置请求响应链上的所有缓存机制必须遵守的指令                              |
| Connection          | 设置当前链接和 hop-by-hop 协议请求字段列表的控制选项                      |
| Content-Disposition | 上传的数据文件                                                            |
| Content-Length      | 设置请求体字节长度                                                        |
| Content-MD5         |                                                                           |
| Content-Type        | 设置请求它的MIME类型                                                      |
| Coolie              | 设置服务器通过Set-Cookie保存 cookie                                       |
| Date                | 消息发送的时间                                                            |
| Expect              | 标识客户端需要的特殊浏览器行为                                            |
| Forwarder           | 披露客户端通过http代理连接web服务的源信息                                 |
| Host                | 设置服务器域名和TCP端口号，如果使用的是服务请求标准端口号，端口号可以省略 |
| If-Match            |                                                                           |
| Origin              | 指示了请求来自于哪个站点                                                  |
| Proxy-Authorization | 为连接代理授权认证信息                                                    |
| Range               | 请求部分实体,设置请求实体的字节数范围                                     |
| Referer             | 上一个页面，url                                                           |
| TE                  | 设置用户代理期望接受的传输编码格式，和响应头中的Transfer-Encoding字段一样 |
| Upgrade             | 请求服务端升级协议                                                        |
| User-Agent          | 用户代理的字符串值                                                        |

### 服务端响应消息

HTTP响应也由四个部分组成，分别是：状态行、消息报头、空行和响应正文。

![响应消息结构](./img/http_response.jpg)

#### 响应头

| name                        | description                                          |
| --------------------------- | ---------------------------------------------------- |
| Access-Control-Allow-Origin | 指定哪些网站可以参与跨站资源共享                     |
| Accept-Patch                | 指定服务器支持的补丁文档格式                         |
| Accept-Range                | 服务器支持的部分内容范围                             |
| Age                         | 对象在代理缓存中暂存的秒数                           |
| Allow                       | 服务器支持的方法                                     |
| Cache-Control               | 告诉客户端所有缓存机制是否缓存对象，单位秒           |
| Content-Disposition         | 文件打开或者保存方式                                 |
| Content-Encoding            | 文档的编码方法                                       |
| Content-Length              | 表示内容长度，只有浏览器使用持久 http 链接是才需要。 |
| Content-Location            | 设置返回数据的另一个位置                             |
| Content-Range               | 响应体内容是完整信息的哪一部分                       |
| Content-Type                | 设置响应体的MIME类型                                 |
| Expires                     | 设置响应体过期时间                                   |
| Last-Modified               | 设置请求对象最后一次的修改日期                       |
| Link                        | 设置与其他资源的类型关系                             |
| Location                    | 在重定向中或者创建新资源时使用                       |
| Refresh                     | 页面定时刷新，或重定向                               |
| Server                      | 服务器名称                                           |
| Set-Cookie                  | 设置HTTP Cookie                                      |
| Status                      | 设置HTTP响应状态                                     |
| Upgrade                     | 请求客户端升级协议                                   |


响应实例：

```http

```

## Method

### 参考

[MDN Http Headers](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers)