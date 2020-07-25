package com.xiong.tblog.service;

import com.xiong.tblog.entity.User;
import com.xiong.tblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    //    该方法重写UserDetailsService的loadUserByUsername方法，
    //    用于WebSecurityConfig类登录时自动去匹配密码
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if (StringUtils.isEmpty(user))
            throw new UsernameNotFoundException("用户名不存在！");
        //返回用户角色
        user.setRoles(userMapper.findAllRolesByUserId(user.getId()));
        return user;
    }
}
