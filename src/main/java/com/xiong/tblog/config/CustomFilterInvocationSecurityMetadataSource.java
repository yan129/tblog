package com.xiong.tblog.config;

import com.xiong.tblog.entity.Menu;
import com.xiong.tblog.entity.Role;
import com.xiong.tblog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 这个类的作用，主要是根据用户传来的请求地址，分析出请求需要的角色
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.findAllMenuWithRole();
        for (Menu menu : menus) {
            //match第一个参数为数据库可访问URL
            if (antPathMatcher.match(menu.getUrl(), requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    str[i] = roles.get(i).getRoleName();
                }
                return SecurityConfig.createList(str);
            }
        }
        //没有匹配上的资源，都是登录访问
        //return SecurityConfig.createList("ROLE_LOGIN");
        //如果没有匹配的url直接返回null，也就是没有配置权限的url默认都为白名单，
        // 想要换成默认是黑名单只要修改这里即可。
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
