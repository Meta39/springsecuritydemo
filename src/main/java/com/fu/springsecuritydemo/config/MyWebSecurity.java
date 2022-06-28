//package com.fu.springsecuritydemo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * 通过配置类实现认证（不建议）
// */
//@Configuration
//public class MyWebSecurity extends WebSecurityConfigurerAdapter {
//    //在方法中配置用户名和密码，作为用户登录的数据
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        auth.inMemoryAuthentication().withUser("lucy").password(bCryptPasswordEncoder.encode("123")).roles();
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
