# WenDev-WebSite

基于`Java 11`，用`Spring Boot`开发的我的个人网站。

官网：http://www.wendev.site

## 技术栈

+ `Spring Boot`——Web框架
+ `MySQL`——关系型数据库
+ `Spring Data JPA` + `Hibernate`——ORM框架
+ `Thymeleaf`——模板引擎

## 版本及更新日志

### 当前版本：1.1.0.RELEASE

+ 完全重写了前端界面，使用的是模板：[Alpha](http://www.cssmoban.com/cssthemes/7053.shtml)
+ 现在的界面是响应式的，基本上可以完美适配移动端
+ 优化了首屏加载速度，但仍然比较慢，将在后续版本优化
+ TODO：后台管理部分由于样式问题暂时没有重写，将在后续版本修复
+ 开始向源代码中添加注释，以防后面维护时自己看不懂

### 1.0.0

+ 用户管理与登录、注册功能
+ 文章的发布、修改、查找与删除
+ 类型、标签的增删改查
+ 文章的评论、回复评论功能
+ 全局异常处理与参数校验

有什么好的建议，或者发现了什么Bug，欢迎提Issue，也欢迎贡献代码提交Pull Request，不胜感激～
