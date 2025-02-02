package com.pzen.server.controller;

import com.pzen.entity.Video;
import com.pzen.utils.Result;
import io.ebean.DB;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class HelloController {

    @GetMapping("/find")
    public Object sayHello() {
        List<Video> list = DB.byName("db").find(Video.class).findList();
        return Result.success(list, null);
    }

}
