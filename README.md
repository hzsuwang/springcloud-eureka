# springcloud-eureka
## 服务注册中心
### eureka-server project 启动一个服务注册中心，只需要一个注解@EnableEurekaServer，这个注解需要在springboot工程的启动application类上加：
    @EnableEurekaServer
    @SpringBootApplication
    public class Application {

        public static void main(String[] args) {
            new SpringApplicationBuilder(Application.class).web(true).run(args);
        }

    }
 ### pom.xml配置 
     <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>

        <groupId>com.iterror.springcloud</groupId>
        <artifactId>eureka-server</artifactId>
        <version>1.0-SNAPSHOT</version>
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>1.3.5.RELEASE</version>
            <relativePath/> <!-- lookup parent from repository -->
        </parent>

        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <java.version>1.8</java.version>
        </properties>

        <dependencies>

            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-eureka-server</artifactId>
            </dependency>

        </dependencies>

        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>Brixton.RELEASE</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>

        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>

    </project>

   ### eureka-server  项目提供了4个配置文件：
        application.properties
        application-peer1.properties
        application-peer2.properties
        application-peer3.properties
   
   ###  单个服务启动，默认激活application.properties 配置
        java -jar eureka-server-1.0-SNAPSHOT.jar
        
   ### 集群配置启动
   
   #### 1：在etc/hosts中配置peer1，peer2，peer3，如下:
        
        127.0.0.1 peer1
        127.0.0.1 peer2
        127.0.0.1 peer3
        
        
   #### 对maven工程编译打包之后，激活相应的配置文件，运行命令：
        
        java -jar eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer1
        java -jar eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer2
        java -jar eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer3
   
   ## 服务生产者
   ### demo-service 启动一个服务接口  默认激活application.properties 配置
        java -jar demo-service-1.0-SNAPSHOT.jar
   ### 或者 demo-service 服务接口启动多个进程 激活相应的配置文件，运行命令：
         java -jar demo-service-1.0-SNAPSHOT.jar --spring.profiles.active=service1
         java -jar demo-service-1.0-SNAPSHOT.jar --spring.profiles.active=service2
         java -jar demo-service-1.0-SNAPSHOT.jar --spring.profiles.active=service3
  
   ##服务消费者  有两种模式    feign 和 ribbon
   
   ### feign
   
   #### DemoClient 源码如下：
       package com.iterror.springcloud.feign.service;
       
       import com.iterror.springcloud.feign.domain.UserDO;
       import org.springframework.cloud.netflix.feign.FeignClient;
       import org.springframework.web.bind.annotation.RequestMapping;
       import org.springframework.web.bind.annotation.RequestMethod;
       import org.springframework.web.bind.annotation.RequestParam;
       
       @FeignClient("demo-service")
       public interface DemoClient {
       
           @RequestMapping(method = RequestMethod.GET, value = "/add")
           Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
       
           @RequestMapping(method = RequestMethod.GET, value = "/get")
           UserDO get(@RequestParam(value = "uid") Integer uid);
       
       }
       
   #### ConsumerController源码如下：
        package com.iterror.springcloud.feign.web;
        
        import com.iterror.springcloud.feign.domain.UserDO;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RestController;
        
        import com.iterror.springcloud.feign.service.DemoClient;
        
        @RestController
        public class ConsumerController {
        
            @Autowired
            DemoClient demoClient;
        
            @RequestMapping(value = "/add", method = RequestMethod.GET)
            public Integer add() {
                return demoClient.add(10, 20);
            }
        
            @RequestMapping(value = "/get", method = RequestMethod.GET)
            public UserDO get() {
                return demoClient.get(10);
            }
        
        }

   ### ribbon
   #### ConsumerController 源码如下：
           package com.iterror.springcloud.ribbon.web;
           import com.iterror.springcloud.ribbon.domain.UserDO;
           import org.springframework.beans.factory.annotation.Autowired;
           import org.springframework.web.bind.annotation.RequestMapping;
           import org.springframework.web.bind.annotation.RequestMethod;
           import org.springframework.web.bind.annotation.RestController;
           import org.springframework.web.client.RestTemplate;
           
           @RestController
           public class ConsumerController {
           
               @Autowired
               RestTemplate restTemplate;
           
               @RequestMapping(value = "/add", method = RequestMethod.GET)
               public String add() {
                   return restTemplate.getForEntity("http://DEMO-SERVICE/add?a=10&b=20", String.class).getBody();
               }
               @RequestMapping(value = "/get", method = RequestMethod.GET)
               public String get() {
                   UserDO user = restTemplate.getForEntity("http://DEMO-SERVICE/get?uid=10", UserDO.class).getBody();
                   System.out.println(user.getUserId());
                   System.out.println(user.getName());
                   user = restTemplate.getForEntity("http://DEMO-SERVICE/get?uid=11", UserDO.class).getBody();
                   System.out.println(user.getUserId());
                   System.out.println(user.getName());
                   return "user";
               }
           
           }
