package com.example.manage.core.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ManageUserDetailService extends UserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
