# API开放平台项目开发笔记

根据鱼皮教程开发

上传地址：https://github.com/PaulEternity/APIPlatform.git



采用SpringBoot Mybatis MybatisPlus Redis Mysql等技术

实现的是一个供给前端进行接口测试的平台后端



## 主要部分：

PaulAPI

所有服务接口的实现，后台服务端

paulapi-interface

测试用客户端

paulapi-client-sdk

设计的一个供客户端使用的基本用户配置，让客户端部分只需要负责接口的创建以及调用。





## 打包文件

用Maven中的install将SDK进行打包，打包后的SDK会存放在本地Maven目录中，这样就可以通过dependency在maven中导入依赖

打包前要注意修改自己SDK中的版本

打包文件时要注意，把原来的测试部分给删掉，其次注意原来的启动类要改为配置类，不然调用的时候会报错（循环导入）





## 网关

一个检票口

这样用户调用时先经过网关，网关可以直接统一地统计次数，就不需要每个接口中增加统计次数的方法

优点：统一进行操作，处理问题

场景：

1. 路由
2. 鉴权
3. 跨域
4. 浏览染色
5. 访问控制
6. 统一业务处理
   1. 缓存
7. 发布控制
8. 负载均衡
9. 接口保护
   1. 限制请求
   2. 信息脱敏
   3. 降级（熔断）
   4. 限流
   5. 超时时间
10. 统一日志
11. 统一文档



### 路由

转发作用，发送请求到对应端口



### 负载均衡

在路由的基础上，把请求随机转发到某个机器



### 统一鉴权

判断用户是否有权限进行操作，无论访问什么接口，都统一判断权限，不需要重复写



### 统一跨域处理

统一进行跨域



### 统一业务处理

同样的业务逻辑拿出来放到上层（网关）

本项目的次数统计即统一业务



### 访问控制

黑白名单 

限制DDOS IP

DDOS攻击：https://blog.csdn.net/Javachichi/article/details/140857056



### 发布控制

灰度发布 上线新接口，先给新接口分配20%流量，老接口80%，再慢慢调整比重



### 流量染色

区分用户来源

排查接口

给请求添加一些标识，一般是设置请求头中，添加新的请求头







## 2024.9.20

```sql
# 用户调用接口次数
create table user_interface_info
(
    id              bigint auto_increment comment '主键'
        primary key,
    userId          bigint                             not null comment '调用用户ID',
    interfaceInfoId bigint                             not null comment '被调用接口ID',
    totalNum        int                                not null comment '总调用次数',
    leftNum         int                                not null comment '剩余调用次数',
    status          int      default 0                 not null comment '接口状态0:关闭,1:开启',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment 'apiplatform.`interface_info`';

```

统计次数的后端开发部分

接口不能暴露

设计好合法检测

- 参数不能为空
- 剩余调用次数不能小于零



请求类



分页查询部分



## 2024 9.21

计算用户调用接口次数实现类（invokeCount）

在user_interface_info中创建一条信息进行测试

方法优化：统计次数的接口是放到原方法中使用的，统计次数可以从原来的方法中抽象出来，每次调用完成之后再进行统计

AOP重点学习

AOP缺点：独立存在于单个项目，本项目中有多个项目



使用网关



## 2024 9.22

全局网关（接入层网关）：负载均衡，请求日志，不和业务逻辑绑定

业务网关（微服务网关）：将请求转发到不同的业务/项目/接口/服务

实现：Spring Cloud Gateway

paulapi-gateway 项目开发

核心概念

路由

断言：一组规则，条件，用来确定如何转发路由

原理：

1. 客户端发起请求
2. Handler Mapping 根据断言，将请求发送到对应路由
3. Web Handler 处理请求
4. 实际调用服务

配置方式

1. 配置式
   1. 简化版
   2. 全称版
2. 编程式



网关配置

```yml
spring:
  cloud:
    gateway:
      mvc:
        routes:
         - id:path_route1
         - uri:
             https://baidu.com
           predicates:
             - Path=/baidu/**
```



```yml
logging:
  level:
    org:
      springframework:
        cloud:
          TRACE //修改日志级别，最低日志也输出
```





过滤器



## 2024 9.23

实现用户统一鉴权 统一的接口调用次数统计

鉴权：accessKey secretKey

跨域问题



业务逻辑：

1. 用户发送请求到API网关
2. 请求日志
3. 黑白名单
4. 用户鉴权
5. 接口是否存在
6. 请求转发，调用模拟接口
7. 响应日志
8. 调用成功，接口调用次数加一 返回给数据库
9. 调用失败，返回规范错误码



请求转发：

把所有路径为*/api/***的请求进行转发，转发到http://localhost:8123/api/**

这一步直接通过yml配置实现



### 报错信息：

```
	at org.springframework.boot.devtools.restart.RestartLauncher.run(RestartLauncher.java:49) ~[spring-boot-devtools-2.7.5.jar:2.7.5]
Caused by: java.io.FileNotFoundException: class path resource [org/springframework/boot/autoconfigure/web/client/RestClientAutoConfiguration.class] cannot be opened because it does not exist
```

解决方案：

1. 检查依赖是否有版本冲突
2. 检查gateway版本和spring boot版本以及JDK版本是否兼容，不兼容就改！



编写业务逻辑

GlobalFilter 全局请求拦截处理

黑白名单

如果不在白名单内，就设置一个状态码，并对其拦截



## 2024 9.24

装饰响应对象，在响应返回给客户端前进行额外处理，记录接口调用次数和日志



## 2024 9.25

RPC：

像调用本地方法一样调用远程方法

RPC向远程服务器发送请求时未必要使用HTTP协议  可选不同协议

Dubbo框架：RPC实现框架    GRPC TRPC

Dubbo底层用的triple协议



## 2024 9.26

Dubbo实现 @EnableDubbo  @DubboService



设置注解

添加配置

注意冲突   Sl4j



整体引入出现问题

### 报错

```
2024-09-26 20:59:15.149 ERROR 19936 --- [  restartedMain] o.a.d.c.deploy.DefaultModuleDeployer     :  [DUBBO] Dubbo Module[1.1.1] start failed: java.lang.IllegalStateException: Failed to register dubbo://192.168.138.238:20880/com.paul.apiPlatform.provider
```

```
2024-09-26 20:59:15.155 ERROR 19936 --- [  restartedMain] o.a.d.c.d.DefaultApplicationDeployer     :  [DUBBO] Dubbo Application[1.1](dubbo-springboot-demo-provider) found failed module: Dubbo Module[1.1.1], dubbo version: 3.0.9, current host: 192.168.138.238


```

```
2024-09-26 20:59:15.158 ERROR 19936 --- [  restartedMain] o.a.d.c.d.DefaultApplicationDeployer     :  [DUBBO] Dubbo Application[1.1](dubbo-springboot-demo-provider) found failed module: Dubbo Module[1.1.1], dubbo version: 3.0.9, current host: 192.168.138.238

```

```
2024-09-26 20:59:17.152 ERROR 19936 --- [  restartedMain] o.a.dubbo.registry.nacos.NacosRegistry   :  [DUBBO] Failed to subscribe provider://192.168.138.238:20880/org.apache.dubbo.metadata.MetadataService?anyhost=true&application=dubbo-springboot-demo-provider&background=false&bind.ip=192
```

```
2024-09-26 20:59:17.540 ERROR 19936 --- [  restartedMain] o.a.d.c.d.DefaultApplicationDeployer     :  [DUBBO] Register instance error, dubbo version: 3.0.9, current host: 192.168.138.238

java.lang.RuntimeException: Request nacos server failed: 
	at org.apache.dubbo.common.function.ThrowableConsumer.execute(ThrowableConsumer.java:51) ~[dubbo-3.0.9.jar:3.0.9]
	at org.apache.dubbo.common.function.ThrowableConsumer.execute(ThrowableConsumer.java:64) ~[dubbo-3.0.9.jar:3.0.9]
	at org.apache.dubbo.registry.nacos.NacosServiceDiscovery.doRegister(NacosServiceDiscovery.java:70) ~[dubbo-3.0.9.jar:3.0.9]
```

可能是我提前引入了nacos并且dubbo的问题没解决

之前网关部分的日志没弄好



怎么就这么慢呢。。。。



## 2024.9.28

是的我鸽了一天的项目哈哈哈哈哈哈哈哈哈

bug修复：

1. 去下nacos  https://github.com/alibaba/nacos/releases

2. nacos本地使用：

   1. 解压（不要放在中文路径）
   2. ### 进入nacos/bin cmd执行startup.cmd -m standalone 端口是8848

3. 去yml配置

   ```yml
   dubbo:
     application:
       name: dubbo-springboot-demo-provider
     protocol:
       name: dubbo
       port: -1
     registry:
       id: nacos-registry
       address: nacos://localhost:8848
   ```

4. pom配置

   ```xml
   <dependency>
               <groupId>com.alibaba.nacos</groupId>
               <artifactId>nacos-client</artifactId>
               <version>2.4.2</version>
    </dependency>
   ```



写公共服务

​	让方法，实体类在多个项目间复用



## 2024 9.29

### 报错信息：

```
D:\StarProject\platform\PaulApi\paulapi-common\target\classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.083 s
[INFO] Finished at: 2024-09-29T22:04:30+08:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.10.1:compile (default-compile) on project paulapi-common: Fatal error compiling: java.lang.NoSuchFieldError: Class com.sun.tools.javac.tree.JCTree$JCImport does not have member field 'com.sun.tools.javac.tree.JCTree qualid' -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException

Process finished with exit code 1

```

### serviceImpl部分报错：

把对应实体类换成公共服务实现后，原来的serviceImpl实现类报错，把Mapper，controller等各处引用过原实体类的地方重新导入，就解决了。



公共服务只保留必要的公共依赖

抽取service和实体类

install本地

提供者引入

消费者引入



## 2024 9.30

公共服务重构完成

gateway部分修改



## 2024 10.1

网关部分报错

### 报错信息：

```
Could not find class [org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration]
```

除此之外还有各式报错

```
Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2024-10-01 15:38:32.713 ERROR 30656 --- [  restartedMain] o.s.boot.SpringApplication               : Application run failed
```

```
2024-10-01 15:38:32.658 ERROR 30656 --- [  restartedMain] o.a.d.c.d.DefaultApplicationDeployer     :  [DUBBO] Dubbo Application[1.1](dubbo-springboot-demo-provider) found failed module: Dubbo Module[1.1.1], dubbo version: 3.2.5, current host: 192.168.138.238, error code: 5-14. This may be caused by , go to https://dubbo.apache.org/faq/5/14 to find instructions. 

```

```

```

```
2024-10-01 15:38:32.638 ERROR 30656 --- [  restartedMain] org.apache.dubbo.config.ReferenceConfig  :  [DUBBO] No provider available., dubbo version: 3.2.5, current host: 192.168.138.238, error code: 2-2. This may be caused by provider not started, go to https://dubbo.apache.org/faq/2/2 to find instructions. 

```



## 2024 10.3

10月2日不是没写，是忘记总结了

整体就是网关没有弄好，还有bug，其余部分一切正常

排除Dubbo，JDK，Nacos的版本问题，可能是环境没有弄好

yml配置里，后端提供方的Dubbo配置和网关的配置要一致，有一个报错可能是短空已经被占用，记得改。

其余部分的报错真的不知道是什么问题，可能要细学一遍Dubbo这部分的内容才能回来改好，如果只是环境问题可能还好说一点，因为项目正常不使用Dubbo那部分是可以运行的。。。

整体就先这样，国庆找时间部署上线一下。



## 2024 10.5

问题解决

nacos注册中心默认了common部分的Dubbo版本是2.0.2，删除之后重启后端重新注册

还有注意注册中心的项目名字和网关的项目文件夹能否对上





打包失败

添加依赖

```
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <testFailureIgnore>true</testFailureIgnore>
                </configuration>
            </plugin>
```

