package com.fu.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 自定义实现认证（建议）
 */
@Configuration
public class MyWebSecurity2 extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;
    //注入数据源
    @Resource
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登出
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();

        //登录
        http.formLogin()
//                .loginPage("/login.html") //登录页面设置
//                .loginProcessingUrl("/user/login")//登陆访问路径
//                .defaultSuccessUrl("/hello").permitAll()//登录成功之后跳转的路径
                .and()
                .authorizeRequests()
                .antMatchers("/", "/user/login").permitAll()//访问白名单
                // 1. hasAuthority方法
//                .antMatchers("/admin").hasAuthority("admin")//具有admin权限才能访问这个路径
                // 2. hasAnyAuthority方法
//                .antMatchers("/admin").hasAnyAuthority("admin,manager")
                // 3. hasRole方法
//                .antMatchers("/admin").hasRole("admin")//配置角色时要加ROLE_   如ROLE_admin
                // 4. hasAnyRole方法
                .antMatchers("/admin").hasAnyRole("admin,test")
                .anyRequest().authenticated()
                //自动登录
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60)//token过期时间秒
                .userDetailsService(userDetailsService)
                .and()
                .csrf().disable();//关闭csrf跨站请求伪造攻击拦截
    }
}
