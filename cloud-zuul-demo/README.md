# cloud-zuul-demo

## zuul+eureka+boot微服务

> 通过zuul进行统一API网关服务，由eureka进行服务注册及发现

通过zuul进行重试、熔断、过滤限制等，如下配置：

```yaml
server:
  port: 8888
spring:
  application:
    name: zuul-gateway-service
#默认路由规则:http://ZUUL_HOST:ZUUL_PORT/微服务在Eureka上的serviceId/**会被转发到serviceId对应的微服务
#自定义路由
#zuul:
#  routes:
#    baidu:
#      path: /it/**
#      url: http://www.ityouknow.com/
#    hello:
#      path: /hello
#      serviceId: ZUUL-EUREKA-PRODUCER
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8000/eureka/
zuul:
  retryable: true #开启重试机制
  ribbon:
    MaxAutoRetries: 2 #设置重试次数
    MaxAutoRetriesNextServer: 0

```
