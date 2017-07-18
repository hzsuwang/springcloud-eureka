# springcloud-eureka
## eureka-server project 启动一个服务注册中心，只需要一个注解@EnableEurekaServer，这个注解需要在springboot工程的启动application类上加：
    @EnableEurekaServer
    @SpringBootApplication
    public class Application {

        public static void main(String[] args) {
            new SpringApplicationBuilder(Application.class).web(true).run(args);
        }

    }
 ## pom.xml配置 
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

   ## eureka-server  项目提供了4个配置文件：
        application.properties
        application-peer1.properties
        application-peer2.properties
        application-peer3.properties
   
   ##  单个服务启动，默认激活application.properties 配置
        java -jar eureka-server-1.0-SNAPSHOT.jar
        
   ## 集群配置启动
   
   ### 1：在etc/hosts中配置peer1，peer2，peer3，如下:
        
        127.0.0.1 peer1
        127.0.0.1 peer2
        127.0.0.1 peer3
        
        
   ### 对maven工程编译打包之后，激活相应的配置文件，运行命令：
        
        java -jar eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer1
        java -jar eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer2
        java -jar eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer3
   