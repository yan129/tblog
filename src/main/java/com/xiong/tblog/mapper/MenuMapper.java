package com.xiong.tblog.mapper;

import com.xiong.tblog.entity.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findAllMenuWithRole();
}
