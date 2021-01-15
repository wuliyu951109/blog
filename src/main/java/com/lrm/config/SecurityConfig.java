//package com.lrm.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // 链式编程
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        // 授权
//        // 主页所有人都可以访问，功能页只有对应有权限的人才能访问
//        // 请求授权的规则
//        httpSecurity.authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .antMatchers("/admin/**").hasRole("admin")
//                .antMatchers("/admin/", "/admin/login.html", "/admin/login").permitAll();
//
//        // 没限默认会到登录页面，需要开启登录页面
//        // /login
//        // httpSecurity.formLogin().loginPage("/admin/");
//
//        // 防止网站工具 get， post
//        // httpSecurity.csrf().disable(); // 关闭csrf的功能 : 登录失败肯存在的原因
//        // httpSecurity.logout().logoutSuccessUrl("/tologin");
//
//        // 开启记住我功能
//        // httpSecurity.rememberMe();
//    }
//
//    // 认证 springboot 2.1.X 可以直接使用~
//    // 密码编码 : PasswordEncoder
//    // 在Spring Security 5.0+ 新增了很多加密方法
//    @Override
//    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//
//        // 这些数据应该从数据库中读
//        authenticationManagerBuilder
//                .inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder)
//                .withUser("wuliyu")
//                .password(passwordEncoder.encode("111111"))
//                .roles("admin");
//    }
//
//}
