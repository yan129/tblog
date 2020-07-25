package com.xiong.tblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiong.tblog.entity.R;
import com.xiong.tblog.filter.LoginFilter;
import com.xiong.tblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthService authService;
    @Autowired
    private CustomUrlDecisionManager urlDecisionManager;
    @Autowired
    private CustomFilterInvocationSecurityMetadataSource securityMetadataSource;
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;
    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private CustomAuthenticationEntryPoint entryPoint;
//    @Autowired
//    private VerificationFilter verificationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    SessionRegistryImpl sessionRegistryImpl(){
        return new SessionRegistryImpl();
    }
    @Bean
    LoginFilter loginFilter() throws Exception{
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(failureHandler);
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/user/doLogin");
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistryImpl());
        sessionStrategy.setMaximumSessions(1);  // 配置允许session最大数1个用户
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        return loginFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/getVerificationCode",
                "/user/getRegisterCode", "/user/register",
                "/css/**","/js/**", "/img/**", "/fonts/**", "/favicon.ico");
    }

    /**
     * 配置拦截规则、表单登录、登录成功或失败响应等
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(verificationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().and().csrf().disable().authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setSecurityMetadataSource(securityMetadataSource);
                        fsi.setAccessDecisionManager(urlDecisionManager);
                        return fsi;
                    }
                })
                .and()
                .logout()
                .logoutUrl("/user/doLogout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
                .and()
                .exceptionHandling()
                //使用authenticationEntryPoint后不会重定向到登录页
                .authenticationEntryPoint(entryPoint);
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistryImpl(), event -> {
            System.out.println("您已在另一台设备登录，非本人操作请修改密码!");
            HttpServletResponse resp = event.getResponse();
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(401);
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(R.error("您已在另一台设备登录，非本人操作请修改密码!")));
            out.flush();
            out.close();
        }), ConcurrentSessionFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
