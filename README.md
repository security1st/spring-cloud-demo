## 微服务示例

- JDK： 11
- Spring Boot: 2.1.7
- Spring Cloud: Greenwich.SR2

### 手动更新配置

```shell script
curl -X POST http://localhost:8090/actuator/bus-refresh
```
该命令将会在Spring Cloud Bus上向所有连接的服务广播配置更新（并非会因为指定了employee-service的端口8090
，就只是更新employee-service），如果需要指定特定服务，需要在url中绑定

参考: [Quick Guide to Microservices with Spring Boot 2.0, Eureka and Spring
 Cloud](https://piotrminkowski.wordpress.com/2018/04/26/quick-guide-to-microservices-with-spring-boot-2-0-eureka-and-spring-cloud/) 
