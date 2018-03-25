package com.sj.config.security.userdetails;

import com.sj.modules.sys.domain.User;
import com.sj.modules.sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by yangrd on 2017/7/3.
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        User user = userRepository.findByLoginName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        return new org.springframework.security.core.userdetails.User(loginName, user.getPassword(), user.isEnabled(), true, true, true, user.listAuthorities());
    }

}