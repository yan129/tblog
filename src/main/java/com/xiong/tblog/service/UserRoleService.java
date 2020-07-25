package com.xiong.tblog.service;

import com.xiong.tblog.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
public interface UserRoleService extends IService<UserRole> {

    int addUserRole(Long userId, Long roleId);
}
