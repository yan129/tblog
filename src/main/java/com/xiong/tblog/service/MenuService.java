package com.xiong.tblog.service;

import com.xiong.tblog.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
public interface MenuService extends IService<Menu> {

    List<Menu> findAllMenuWithRole();
}
