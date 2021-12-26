package com.example.manage.customer.service;

import com.example.manage.core.ResultCodes;
import com.example.manage.core.exception.CustomException;
import com.example.manage.customer.entity.Customer;
import com.example.manage.customer.payload.request.CustomerRequest;
import com.example.manage.customer.payload.response.CustomerResponse;
import com.example.manage.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public CustomerResponse joinCustomer(CustomerRequest customerRequest){

            Customer joinUser = customerRepository.saveAndFlush(Customer.builder()
                    .email(customerRequest.getEmail())
                    .passwd(encoder.encode(customerRequest.getPasswd()))
                    .build()
            );

            return CustomerResponse.builder()
                    .uid(joinUser.getUid())
                    .email(joinUser.getEmail())
                    .build();

    }

    public CustomerResponse getCustomerFromEmail(String email){
        Optional<Customer> searchResult = Optional.ofNullable(customerRepository.findByEmail(email));

        if(searchResult.isPresent()){
            return CustomerResponse.builder()
                    .uid(searchResult.get().getUid())
                    .email(searchResult.get().getEmail())
                    .build();
        }
        else {
            throw new CustomException(ResultCodes.USER_NOT_FOUND_EXCEPTION);
        }
    }

    public Customer getCustomer(Long uid){
        return customerRepository.findById(uid).orElseThrow(()-> new CustomException(ResultCodes.USER_NOT_FOUND_EXCEPTION));
    }
}
