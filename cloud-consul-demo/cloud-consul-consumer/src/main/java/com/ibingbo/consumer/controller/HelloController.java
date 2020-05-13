package com.ibingbo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/hello")
    public String hello() {
        ServiceInstance instance = loadBalancerClient.choose("service-producer");
        System.out.println("address: " + instance.getUri());
        System.out.println("name: " + instance.getServiceId());
        String result = new RestTemplate().getForObject(instance.getUri().toString() + "/hello", String.class);
        System.out.println(result);
        return result;
    }
}
