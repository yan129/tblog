package com.xiong.tblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiong.tblog.entity.R;
import com.xiong.tblog.entity.User;
import com.xiong.tblog.mapper.UserMapper;
import com.xiong.tblog.service.UserRoleService;
import com.xiong.tblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.tblog.utils.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public R register(String username, String password) {
//        String pattern = "^[1][34578]\\d{9}$";
//        boolean bool = RegexUtil.regexData(username, pattern);
//        if (bool){
//
//        }
        //如果用户名存在，返回错误
        if (!StringUtils.isEmpty(findUserByUsername(username)))
            return R.error("用户名已存在！");
        String encode = bCryptPasswordEncoder.encode(password);
        String nickname = UUID.randomUUID().toString().substring(0, 4);
        User user = new User();
        user.setNickname(nickname);
        user.setUsername(username);
        user.setPassword(encode);
        baseMapper.insert(user);
        Long userId = user.getId();
        userRoleService.addUserRole(userId, 2L);
        return R.ok("注册成功！");
    }

    @Override
    public User findUserByUsername(String username) {
//        return baseMapper.findUserByUsername(username);
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}
