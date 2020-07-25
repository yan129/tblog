package com.xiong.tblog.service.impl;

import com.xiong.tblog.entity.UserRole;
import com.xiong.tblog.mapper.UserRoleMapper;
import com.xiong.tblog.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    @Transactional
    public int addUserRole(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUid(userId);
        userRole.setRid(roleId);
        return baseMapper.insert(userRole);
    }
}
