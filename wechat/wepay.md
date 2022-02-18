# wepay 爬坑

sign 大写

## 微信公众号

### 需要资料

- merchantId: 商户号, 商户平台 --> 账号中心
- appid: 微信号, 微信公众号平台 --> 开发 --> 基本配置
- appSecret: APPID对应的接口密码，用于获取接口调用凭证access_token时使用。微信公众号平台 --> 开发 --> 基本配置
- domain: 微信配置域名, 备案且只能是 80 或 443 端口, 微信公众号平台 --> 开发 --> 基本配置
- appKey(key): **必须32位**,交易过程生成签名的密钥, 微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置, (注: 需要api安全操作密码, ie 环境下安装)
- 服务器IP: 域名公网IP, 配置白名单

### 公众号平台配置

#### [公众号设置](https://mp.weixin.qq.com/cgi-bin/settingpage?t=setting/index)

地址: 微信公众号 --> 设置 --> 公众号设置 --> 功能设置

- 校验文件配置: 用于配置接口等, 路径: 微信公众号平台 --> 开发 --> 基本配置
- 业务域名: `domain`
- JS接口安全域名: `domain`
- 网页授权域名: `domain`

NOTE: 根据实际域名(domain)和context-path配置

#### [公众号开发基本设置](https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev)

地址: 微信公众号 --> 开发 --> 基本设置 --> 开发信息

- 开发者密码(AppSecret)
- IP 白名单:  用于获取access_token

#### [服务器配置](https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev)

地址: 微信公众号 --> 开发 --> 基本设置 --> 服务器配置

- 服务器地址(URL):
- 令牌(Token):
- 启用

#### API 安全配置()

地址: 微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置

- key: 交易过程生成签名的密钥 (注: 需要api安全操作密码, ie 环境下安装)
