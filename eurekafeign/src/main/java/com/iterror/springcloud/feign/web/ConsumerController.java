package com.iterror.springcloud.feign.web;

import com.iterror.springcloud.feign.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iterror.springcloud.feign.service.DemoClient;

@RestController
public class ConsumerController {

    @Autowired
    DemoClient demoClient;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add() {
        return demoClient.add(10, 20);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public UserDO get() {
        return demoClient.get(10);
    }

}
