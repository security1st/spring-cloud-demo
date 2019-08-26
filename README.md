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

## docker
### rabbitmq
```shell script
docker pull rabbitmq:management
docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management
```
访问管理界面的地址就是 http://localhost:15672，可以使用默认的账户登录，用户名和密码都guest

### redis
```shell script
docker pull redis:latest
mkdir ~/redis/{conf,data} -p
cd ~/redis
wget https://raw.githubusercontent.com/antirez/redis/4.0/redis.conf -O conf/redis.conf

# 直接替换编辑
sed -i 's/logfile ""/logfile "access.log"/' conf/redis.conf
sed -i 's/# requirepass foobared/requirepass 123456/' conf/redis.conf
sed -i 's/appendonly no/appendonly yes/' conf/redis.conf

# 命令分解
docker run \
-p 6379:6379 \ # 端口映射 宿主机:容器
-v $PWD/data:/data:rw \ # 映射数据目录 rw 为读写
-v $PWD/conf/redis.conf:/etc/redis/redis.conf:ro \ # 挂载配置文件 ro 为readonly
--privileged=true \ # 给与一些权限
--name myredis \ # 给容器起个名字
-d redis redis-server /etc/redis/redis.conf # deamon 运行容器 并使用配置文件启动容器内的 redis-server 

# redis-cli 访问
docker run -it --link myredis:redis --rm redis redis-cli -h redis -p 6379
# 或者
docker exec -it myredis bash
redis-cli
```
参考: [docker 安装部署 redis（配置文件启动）](https://segmentfault.com/a/1190000014091287)

## 其他
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

## 参考
- [Quick Guide to Microservices with Spring Boot 2.0, Eureka and Spring
 Cloud](https://piotrminkowski.wordpress.com/2018/04/26/quick-guide-to-microservices-with-spring-boot-2-0-eureka-and-spring-cloud/) 
