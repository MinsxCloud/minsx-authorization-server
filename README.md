# Minsx-Authorization-Server
#### Minsx-authorization-server是Minsx-Framework家庭成员的一部分，主要负责统一Minsx周边系统的单点登录与权限问题

### 项目说明
+ 软件名称：Minsx-Authorization-Server
+ 版本号：1.0.0
+ 开发者：www.minsx.com
+ 语言：JAVA/HTML/CSS/JS/THYMELEAF
+ 功能：主要用于分离周边系统用户登录以及注册模块,对用户公用权限进行授权(公用权限用于所有周边系统)
+ 优点：与各应用端解耦合,实现单点登录,便于用户维护
+ 缺点：需独立部署,某些情况下Minsx-Framework中涉及到用户的改动需同步到该项目中,以及Cookie中Token的维护等
+ 开源协议：Apache License Version 2.0 http://www.apache.org/licenses/

### 技术选型
+ Spring Boot 基础后端框架
+ Spring Security 安全框架
+ spring Data JPA ORM框架
+ spring Security Oauth2.0 应用授权框架
+ spring Thymeleaf 模板引擎
+ spring Aop 切面模块

### 适用场景
+ 适用于单点登录系统
+ 需要分离用户系统的架构
+ 配合Minsx-Framework使用 [点此查看](https://github.com/MinsxCloud/minsx-center-server)

### 项目演示
+ [点此查看项目演示](https://account.minsx.com/login?redir=https%3A%2F%2Fadmin.minsx.com)

### 项目截图 (V2.0.0版本)
![登录](https://raw.githubusercontent.com/MinsxCloud/minsx-authorization-server/master/doc/image/login.png "登录")


