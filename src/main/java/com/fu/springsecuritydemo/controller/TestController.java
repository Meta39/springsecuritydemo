package com.fu.springsecuritydemo.controller;

import com.fu.springsecuritydemo.entity.Users;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("hello")
    public String hello(){
        if (!redisTemplate.hasKey("lucy")){
            Users users = new Users();
            users.setId(1);
            users.setUsername("lucy");
            users.setPassword("123");
            redisTemplate.opsForValue().set(users.getUsername(),users);
        }
        return "hello";
    }

    @GetMapping("hello2")
    //@Secured({"ROLE_admin"})//需要多个角色才能访问则用英文逗号配置如ROLE_admin,ROLE_test
    @PreAuthorize("hasAnyAuthority('admin')")//需要多个权限（不是角色）用逗号
    public String hello2(){
        return "hello2";
    }
}
