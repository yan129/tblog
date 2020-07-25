package com.xiong.tblog.controller;


import com.xiong.tblog.entity.Blog;
import com.xiong.tblog.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiong
 * @since 2020-05-22
 */
@RestController
@Slf4j
@RequestMapping("/tb/normal")
public class BlogController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/saveBlog")
    public R saveBlog(@RequestBody @Valid Blog blog, BindingResult errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return R.error(message);
        }
        log.error(blog.toString());
        return null;
    }

}
