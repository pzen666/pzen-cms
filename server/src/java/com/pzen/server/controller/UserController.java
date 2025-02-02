package com.pzen.server.controller;

import com.pzen.dto.UserDTO;
import com.pzen.entity.User;
import com.pzen.server.service.UserService;
import com.pzen.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/save")
    public Result<Object> save(@RequestBody UserDTO dto) {
        User u = userService.add(dto);
        return Result.success(u, null);
    }

    @RequestMapping("/delete")
    public Result<Object> delete(@RequestBody UserDTO dto) {
        User u = userService.del(dto);
        return Result.success(u, null);
    }
    @RequestMapping("/update")
    public Result<Object> update(@RequestBody UserDTO dto) {
        User u = userService.update(dto);
        return Result.success(u, null);
    }
    @RequestMapping("/findOne")
    public Result<User> findOne(@RequestBody UserDTO dto) {
        User u = userService.findOne(dto);
        return Result.success(u, null);
    }

}
