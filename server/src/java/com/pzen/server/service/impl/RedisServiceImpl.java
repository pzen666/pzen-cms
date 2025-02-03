package com.pzen.server.service.impl;

import com.pzen.server.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {


    @Resource
    @Qualifier("objectRedisTemplate0")
    private RedisTemplate<String, Object> objectRedisTemplate0;

    @Resource
    @Qualifier("objectRedisTemplate1")
    private RedisTemplate<String, Object> objectRedisTemplate1;

//    @Resource
//    @Qualifier("redisTemplate0")
//    private StringRedisTemplate getRedisTemplate;

    @Override
    public Object find0() {
        objectRedisTemplate0.opsForValue().set("test", "test");
        return objectRedisTemplate0.opsForValue().get("test");
    }

    @Override
    public Object find1() {
        objectRedisTemplate1.opsForValue().set("test", "test");
        return objectRedisTemplate1.opsForValue().get("test");
    }
}
