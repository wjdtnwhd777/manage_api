package com.example.manage.customer.service;

import com.example.manage.core.encryption.Crypt;
import com.example.manage.customer.entity.Customer;
import com.example.manage.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerServiceTest {

    @Autowired CustomerRepository customerRepository;

    @Test
    void 유저등록_테스트() throws Exception {
        //given
        String passwd = Crypt.encrypt("1234");

        //when
        Customer customer = customerRepository.saveAndFlush(
                Customer.builder()
                .email("qlwusxpr0115@naver.com")
                .passwd(passwd)
                .build()
        );

        //then
        customerRepository.findById(customer.getUid()).ifPresent(findResult->{
            assertThat(findResult.getPasswd()).isEqualTo(passwd);
            assertThat(findResult.getEmail()).isEqualTo("qlwusxpr0115@naver.com");
        });
    }
}