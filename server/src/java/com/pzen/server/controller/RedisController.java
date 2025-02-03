package com.pzen.server.controller;

import com.pzen.server.service.RedisService;
import com.pzen.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("find0")
    public Result<Object> find0() {
        Object o = redisService.find0();
        return Result.success(o);
    }

    @GetMapping("find1")
    public Result<Object> find1() {
        Object o = redisService.find1();
        return Result.success(o);
    }


}
