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