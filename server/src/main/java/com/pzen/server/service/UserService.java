package com.pzen.server.service;

import com.pzen.dto.UserDTO;
import com.pzen.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    User add(UserDTO dto);

    User del(UserDTO dto);

    User update(UserDTO dto);

    User findOne(UserDTO dto);

    User getUserInfo(HttpServletRequest request);
}
