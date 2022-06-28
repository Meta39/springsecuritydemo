package com.fu.springsecuritydemo.service;

import com.fu.springsecuritydemo.entity.Users;
import com.fu.springsecuritydemo.mapper.UsersMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用usersMapper根据用户名查询数据库
        Users users = usersMapper.select(username);
        //判断
        if (users == null){//数据库没有用户名，认证失败
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_admin");//admin是权限，ROLE_admin是角色
        //从查询数据库返回users对象，得到用户名和密码，返回
        return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }
}
