package com.pzen.server.service;

import com.pzen.dto.UserDTO;
import com.pzen.entity.User;

public interface UserService {

    User add(UserDTO dto);

    User del(UserDTO dto);

    User update(UserDTO dto);

    User findOne(UserDTO dto);

}
