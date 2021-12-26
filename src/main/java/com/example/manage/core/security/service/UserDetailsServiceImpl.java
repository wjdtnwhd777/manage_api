package com.example.manage.core.security.service;

import com.example.manage.core.exception.UserNotFoundException;
import com.example.manage.customer.entity.Customer;
import com.example.manage.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ManageUserDetailService {
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Customer customer = Optional.ofNullable(customerRepository.findByEmail(email))
                .orElseThrow(() -> new UserNotFoundException(String.format("해당 사용자를 찾을 수 없습니다. 이메일: %s", email)));

        return UserDetailsImpl.build(customer);
    }

}
