## spring-cloud-gateway

这里通过注册中心consul搭建分布式远程API服务，网关API通过注册中心中注册的服务进行路由转发，动态调用相应微服务的API进行远程通信


```yaml
server:
  port: 8080
spring:
  cloud:
    ##指定注册中心
    consul:
      host: localhost
      port: 8500
      #下面的可选,如果有则在consul管理页面可以显示该服务
      discovery:
        register: true
        service-name: gateway-service
        instance-id: ${spring.application.name}:${server.port}
    gateway:
      discovery:
        locator:
          enabled: true #打开与注册中心交互并使用注册中心中相应的服务API
      routes:
        - id: hello-service
          uri: lb://consul-producer #指定远程服务，动态均衡
          predicates:
            - Path=/hello
        - id: hi-service
          uri: lb://consul-producer #指定远程服务，动态均衡
          filters:
            - AddRequestParameter=name, bill #添加参数name=bill
          predicates:
            - Method=GET
            - Path=/hi
  application:
    name: gateway-middle-service
logging:
  level:
    org.springframework.cloud.gateway: debug
```


