# springboot-ebean-jdk21  DEMO

### 说明
#### License
```
https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh-hans
```
#### 开发工具安装必备插件
* 本项目使用Ebean进行ORM操作,需要在idea中安装插件: Ebean enhancement,安装后在构建菜单中选择Ebean Enhancement,然后点击选中即可.
#### 项目构成
* Redis多数据源
* PgSQL
* Ebean ORM
* JWT
* 接口加密注解
* XXS SQL注入拦截器
* Modbus从站代码-长连接版，模拟WebSocket形式
* Modbus主站代码-长连接版，模拟WebSocket形式


#### Github 解除ssl验证
```
fatal: unable to access 'https://github.com/xxxxx.git/': OpenSSL SSL_read: Connection was reset, errno 10054；
git config --global http.sslVerify false
git config --global https.sslVerify false

fatal: unable to access 'https://github.com/xxxxx.git/': Failed to connect to github.com port 443 after 21126 ms: Timed out；
git config --global --unset http.proxy
git config --global --unset https.proxy


```







