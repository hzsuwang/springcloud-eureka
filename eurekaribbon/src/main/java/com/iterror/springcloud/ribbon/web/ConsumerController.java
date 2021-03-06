package com.iterror.springcloud.ribbon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.iterror.springcloud.ribbon.domain.UserDO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return restTemplate.getForEntity("http://DEMO-SERVICE/add?a=10&b=20", String.class).getBody();
    }

    public String fallback() {
        return "some exception occur call fallback method.";
    }

    @HystrixCommand(fallbackMethod = "fallback")
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
