package com.pzen.server.service.impl;

import com.pzen.dto.UserDTO;
import com.pzen.entity.User;
import com.pzen.server.service.UserService;
import com.pzen.server.utils.EntityHelper;
import com.pzen.server.utils.JwtUtil;
import com.pzen.server.utils.QueryConditionBuilder;
import io.ebean.DB;
import io.ebean.Query;
import io.ebean.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User add(UserDTO dto) {
        User u = new User();
        EntityHelper.convertDtoToEntity(dto, u);
//        test.save();
        DB.byName("db").save(u);
        return u;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User del(UserDTO dto) {
        User u = DB.find(User.class).where().eq("id", dto.getId()).findOne();
        if (u != null) {
            DB.byName("db").delete(u);
        }
        return u;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User update(UserDTO dto) {
        User u = DB.find(User.class).where().eq("id", dto.getId()).findOne();
        EntityHelper.convertDtoToEntity(dto, u);
        if (u != null) {
            DB.byName("db").update(u);
        }
        return u;
    }

    @Override
    public User findOne(UserDTO dto) {
        Query<User> query = DB.byName("db").find(User.class);
        // 使用工具类构建查询条件
        QueryConditionBuilder.buildConditions(query, dto.getConditions());
        return query.findOne();
    }

    @Override
    public User getUserInfo(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null) {
            new LoginException("重新登陆!");
        }
        User u = jwtUtil.getUserDetailsFromToken(token);
        if (u != null) {
            return u;
        } else {
            new LoginException("重新登陆!");
        }
        return u;
    }

}
