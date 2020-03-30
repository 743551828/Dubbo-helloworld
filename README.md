@[toc](目录)
# 一、基础知识
## 1、分布式基础理论
### 1.1 什么是分布式
分布式系统是若干独立计算机的集合，这些计算机对于用户来说就像单个相关系统。
分布式系统（distributed system）是建立在网络之上的软件系统。
### 1.2 发展演变

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200330092519163.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzE2MjYx,size_16,color_FFFFFF,t_70)

### 1.3 RPC

 1. 什么叫rpc：Remote Procedure Call ，是一种进程间的通信方式，是一种技术思想，而不是规范。
 2. RPC基本原理![在这里插入图片描述](https://img-blog.csdnimg.cn/20200330092758315.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzE2MjYx,size_16,color_FFFFFF,t_70)
RPC两个核心模块：通讯、序列化。
RPC框架有很多：Dubbo、GRPC、Thrift、HSF（High Speed Service Framework） 
## 2、Dubbo核心概念
基本架构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200330094522651.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzE2MjYx,size_16,color_FFFFFF,t_70)
Registry：注册中心
Consumer：消费者
Provider：生产者
Container：服务容器
Monitor：监测中心
## 3、注册中心：zookeeper下载及安装
[官网下载](http://zookeeper.apache.org/releases.html)
1、将bin目录下的zoo_sample.cfg复制一份，改名为zoo.cfg，并将其中的dataDir改为dataDir=../data
2、在zookeeper的根目录下创建data文件夹
3、在bin目录下打开cmd，运行zkServer.cmd服务端，显示运行成功。
4、在bin目录下打开cmd，运行zkCli.cmd客户端，输入zookeeper基本命令。
## 4、监控中心
在Dubbo[官网github](https://github.com/apache/dubbo)中clone Dubbo admin
 3. 老版本前后端不分离，直接在dubbo-admin文件夹中mvn clean package 打成jar包，再java -jar dubbo-admin-0.0.1-SNAPSHOT.jar 运行项目，在网页上输入localhost:7001即可
 4. 新版本dubbo-admin 先后端分离，在启动zookeeper后，想要看到监控，需要先下载dubbo-admin 然后 在server中打包，使用mvn package -Dmaven.test.skip=true ,然后 java -jar 启动 之后看不到页面，因为前后端分离了，再去admin-ui 里面执行 npm install 等待下载前端的依赖；完毕后启动前端 npm run dev 之后 localhost:8081就可以看到现在的后台管理页面了，当然dubbo-server 必须启动起来
## 5、Dubbo-helloworld
某个电商体统，订单服务需要调用用户服务获取某个用户的所有地址；
我们现在需要创建两个服务模块进行测试：
|模块|服务|
|-|-|
|订单web模块|创建订单等|
|用户服务service模块|查询用户地址等|
测试预期结果：
订单服务web模块在A服务器，用户服务模块在B服务器，A可以远程调用B的功能
 1. 创建user-service-provider用户服务（服务提供者）
 2. 创建order-service-provider订单服务（服务消费者）
 3. 创建interface中转站，用来存放接口和javabean，并将该依赖导入前两个服务
 4. 将服务提供者注册到注册中心
（1）导入dubbo依赖（2.6.2）和操作zookeeper的客户端（curator）

```java
		<!--引入dubbo-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.6.2</version>
        </dependency>

        <!--由于注册中心使用的是zookeeper，所有需要引入zookeeper的操作客户端-->
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>2.12.0</version>
        </dependency>

```

（2） 配置服务提供者
创建provider.xml配置文件，将userServiceProvide接口暴露出来

```bash
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://code.alibabatech.com/schema/dubbo
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

    
    
    <!--1、指定当前服务/应用的名字（同样的名字服务相同，不要和别的服务同名）-->
    <dubbo:application name="user-service-provider"></dubbo:application>
    
    <!--2、指定注册中心的名字-->
    <!--<dubbo:registry address="zookeeper://127.0.0.1:2181"></dubbo:registry>-->
    <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181"></dubbo:registry>
    
    <!--3、指定通信规则（通信协议、通信端口）-->
    <dubbo:protocol name="dubbo" port="20880"></dubbo:protocol>

    <!--4、暴露服务,ref,指向服务的真正实现-->
    <dubbo:service interface="com.zys.service.UserService" ref="userServiceImpl"></dubbo:service>

    <bean id="userServiceImpl" class="com.zys.service.impl.UserServiceImpl"></bean>
</beans>


```

 5. 将服务消费者注册到注册中心
	
（1）pom.xml文件配置同服务提供者
（2）配置provide.xml文件

```bash
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://code.alibabatech.com/schema/dubbo
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
        http://www.springframework.org/schema/tx
        ">

    <context:component-scan base-package="com.zys.service.impl"></context:component-scan>

    <dubbo:application name="order-service-provider"></dubbo:application>

    <dubbo:registry address="zookeeper://127.0.0.1:2181"></dubbo:registry>

    <!--声明需要调用的远程服务接口，生成远程服务代理-->
    <dubbo:reference interface="com.zys.service.UserService" id="userService"></dubbo:reference>


</beans>

```
 6. 编写MainApplication测试
 生产者
 

```java
package com.zys.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zhangys
 * @description
 * @date 2020/3/30
 */
public class MainApplication {


    public static void main(String[] args){
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("provider.xml");
        ioc.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

```
消费者

```java
package com.zys;

import com.zys.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zhangys
 * @description
 * @date 2020/3/30
 */
public class MainApplication {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("provider.xml");

        OrderService orderService = ioc.getBean(OrderService.class);
        orderService.initOrder("1");
        System.in.read();

    }



}

```

 运行成功![在这里插入图片描述](https://img-blog.csdnimg.cn/20200330170657744.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNzE2MjYx,size_16,color_FFFFFF,t_70)
 [完整代码](https://github.com/743551828/Dubbo-helloworld)
