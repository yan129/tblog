package com.xiong.tblog.service;

import com.xiong.tblog.entity.R;
import com.xiong.tblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
public interface UserService extends IService<User> {

    R register(String username, String password);

    User findUserByUsername(String username);

}
