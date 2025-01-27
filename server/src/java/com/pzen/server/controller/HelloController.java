package com.pzen.server.controller;

import com.pzen.Result;
import com.pzen.entity.Video;
import io.ebean.DB;
import io.ebean.Database;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class HelloController {

    private final Database db = DB.getDefault();

    @GetMapping("/find")
    public Object sayHello() {
        List<Video> list = db.find(Video.class).findList();
        return Result.success(list, null);
    }

}
