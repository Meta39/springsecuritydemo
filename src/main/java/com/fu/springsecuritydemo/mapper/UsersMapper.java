package com.fu.springsecuritydemo.mapper;

import com.fu.springsecuritydemo.entity.Users;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用redis模拟查询数据库
 */
@Component
public class UsersMapper {
    @Resource
    private RedisTemplate redisTemplate;

    public void insert(Users users){
        redisTemplate.opsForValue().set(users.getId(),users);
    }

    public Users select(String username){
        return (Users) redisTemplate.opsForValue().get(username);
    }

    public void delete(Integer userId){
        redisTemplate.delete(userId);
    }
}
