## spring-cloud-gateway

这里通过注册中心consul搭建分布式远程API服务，网关API通过注册中心中注册的服务进行路由转发，动态调用相应微服务的API进行远程通信

> 这里重点结合限流、熔断、重试等进行服务治理


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
        - id: say-hello-service
          uri: lb://consul-producer #指定远程服务，动态均衡
          filters:
            - StripPrefix=1 #过滤掉第一个path,ej:/say/hello->/hello
          predicates:
            - Method=GET
            - Path=/say/**
        - id: limit-hello-service
          uri: lb://consul-producer #指定远程服务，动态均衡
          filters:
            - StripPrefix=1 #过滤掉第一个path,ej:/say/hello->/hello
            - name: RequestRateLimiter #通过redis进行限流设置
              args:
                redis-rate-limiter.replenishRate: 10
                redis-rate-limiter.burstCapacity: 20
                key-resolver: "#{@userKeyResolver}"
          predicates:
            - Method=GET
            - Path=/limit/**
        - id: retry-hello-service
          uri: lb://consul-producer #指定远程服务，动态均衡
          filters:
            - name: Retry #重试机制设置
              args:
                retries: 3
                statuses: BAD_GATEWAY
          predicates:
            - Method=GET
            - Path=/retry
        - id: hystrix_route #熔断设置，服务不可用进行跳转
          uri: https://www.baidu.com
          filters:
            - Hystrix=myCommandName
          predicates:
            - Method=GET
            - Path=bad
        - id: hystrix_route_callback #熔断回调处理设置，服务不可用进行跳转
          uri: https://www.baidu.com
          filters:
            - name: Hystrix
              args:
                name: fallbackcmd
                fallbackUri: forward:/hello
          predicates:
            - Method=GET
            - Path=/callback

  application:
    name: gateway-middle-service
  redis: #指定redis服务
    host: localhost
    port: 6379
logging:
  level:
    org.springframework.cloud.gateway: debug
```


