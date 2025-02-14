package com.pzen.server.controller;

import com.pzen.dto.UserDTO;
import com.pzen.entity.User;
import com.pzen.server.service.UserService;
import com.pzen.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final WebApplicationContext webApplicationContext;

    public UserController(UserService userService, WebApplicationContext webApplicationContext) {
        this.userService = userService;
        this.webApplicationContext = webApplicationContext;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public Result<Object> save(@RequestBody UserDTO dto) {
        User u = userService.add(dto);
        return Result.success(u, null);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Object> delete(@RequestBody UserDTO dto) {
        User u = userService.del(dto);
        return Result.success(u, null);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public Result<Object> update(@RequestBody UserDTO dto) {
        User u = userService.update(dto);
        return Result.success(u, null);
    }

    @PostMapping("/findOne")
    @PreAuthorize("hasRole('USER')")
    public Result<User> findOne(@RequestBody UserDTO dto) {
        User u = userService.findOne(dto);
        return Result.success(u, null);
    }

    @GetMapping("/getUserInfo")
    public Result<Object> getUserInfo(HttpServletRequest request) {
        User u = userService.getUserInfo(request);
        return Result.success(u);
    }

}
