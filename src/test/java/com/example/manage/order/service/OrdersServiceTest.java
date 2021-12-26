package com.example.manage.order.service;

import com.example.manage.customer.entity.Customer;
import com.example.manage.customer.repository.CustomerRepository;
import com.example.manage.goods.entity.Goods;
import com.example.manage.goods.repository.GoodsRepository;
import com.example.manage.order.entity.Orders;
import com.example.manage.order.repository.OrdersRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrdersServiceTest {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Test
    @Order(1)
    @DisplayName("1. 주문등록 테스트")
    void 주문등록_테스트(){
        //given & when
        Orders givenObject = ordersRepository.saveAndFlush(Orders.builder()
                .customer(customerRepository.saveAndFlush(
                                Customer.builder()
                                        .email("qlwusxpr0115@naver.com")
                                        .passwd("1234")
                                        .build()
                        )
                )
                .goods(goodsRepository.saveAndFlush(
                                Goods.builder()
                                        .goodsName("배")
                                        .goodsPrice(15000)
                                        .build()
                        )
                )
                .build()
        );

        //then
        assertThat(givenObject.getUid()).isNotNull();
        assertThat(givenObject.getCustomer().getEmail()).isEqualTo("qlwusxpr0115@naver.com");
    }

    @Test
    @Order(2)
    @DisplayName("2. 전체 주문 조회 테스트")
    void 주문조회_테스트(){
        //when
        List<Orders> testList = ordersRepository.findAll();

        //then
        assertThat(testList).isNotNull();
        assertThat(testList.size() > 0 ).isTrue();
    }

    @Test
    @Order(3)
    @DisplayName("3. 주문 상세 조회 테스트")
    void 주문상세조회_테스트(){
        //when
        Orders test = ordersRepository.getById(1L);
        assertThat(test).isNotNull();
        assertThat(test.getCustomer().getEmail()).isEqualTo("qlwusxpr0115@naver.com");
        assertThat(test.getGoods().getGoodsName()).isEqualTo("배");
    }

}