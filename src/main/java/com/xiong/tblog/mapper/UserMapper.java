package com.xiong.tblog.mapper;

import com.xiong.tblog.entity.Role;
import com.xiong.tblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
public interface UserMapper extends BaseMapper<User> {
//    User findUserByUsername(String username);

    List<Role> findAllRolesByUserId(Long id);
}
