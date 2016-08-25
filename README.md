平时工作需要经常ssh登录linux查看应用运行日志，这样很麻烦，要是在页面上直接显示应用运行日志就方便了，不需要登录每个系统去看。
浏览器和服务器端的请求一般是无状态http请求，要想实时显示日志就要不断定时ajax请求轮训，
但有新的日志内容时就需要由服务器主动推送给客户端浏览器。这里使用WebSocket协议，进行长连接，实时输出响应。


## Getting Started
在Linux下用Maven Jetty插件运行Demo：
```
git clone https://github.com/pandboy/RealTimeLogShow.git
cd RealTimeLogShow
./run.sh
```
成功后可以在http://localhost:9999/查看实时日志。
运行的时候依赖maven，要先安装maven
## 部署
可以将项目部署在支持JSR 356的服务器，这里用Jetty
