package com.xiong.tblog.service.impl;

import com.xiong.tblog.entity.Menu;
import com.xiong.tblog.mapper.MenuMapper;
import com.xiong.tblog.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> findAllMenuWithRole() {
        return baseMapper.findAllMenuWithRole();
    }
}
