package com.xiong.tblog.service.impl;

import com.xiong.tblog.entity.Blog;
import com.xiong.tblog.mapper.BlogMapper;
import com.xiong.tblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiong
 * @since 2020-05-22
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
