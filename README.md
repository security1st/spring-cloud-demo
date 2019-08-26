# 微服务示例

## 环境
- JDK： 11
- Spring Boot: 2.1.7
- Spring Cloud: Greenwich.SR2

## 基本功能
- Euraka(Cluster)
- Spring Config Server
- Spring Cloud Gateway
- Zuul
- Swagger2
- 调用链跟踪(Sleuth + Zipkin)
- 配置动态更新(Github)
- docker示例
- 限速
- 回退（fallback）
- webflux(响应式编程)

### 手动更新配置
```shell script
curl -X POST http://localhost:8090/actuator/bus-refresh
```
该命令将会在Spring Cloud Bus上向所有连接的服务广播配置更新（并非会因为指定了employee-service的端口8090
，就只是更新employee-service），如果需要指定特定服务，需要在url中绑定

### Eureka集群启动
```shell script
java -jar eureka-cluster-service-1.0.0-SNAPSHOT.jar --spring.profiles.active=eureka1
java -jar eureka-cluster-service-1.0.0-SNAPSHOT.jar --spring.profiles.active=eureka2
java -jar eureka-cluster-service-1.0.0-SNAPSHOT.jar --spring.profiles.active=eureka3
```

参考: [Quick Guide to Microservices with Spring Boot 2.0, Eureka and Spring
 Cloud](https://piotrminkowski.wordpress.com/2018/04/26/quick-guide-to-microservices-with-spring-boot-2-0-eureka-and-spring-cloud/) 
