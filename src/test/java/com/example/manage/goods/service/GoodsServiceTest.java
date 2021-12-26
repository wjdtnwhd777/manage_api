package com.example.manage.goods.service;

import com.example.manage.goods.entity.Goods;
import com.example.manage.goods.repository.GoodsRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GoodsServiceTest {

    @Autowired
    GoodsRepository goodsRepository;

    @Test
    @Order(1)
    void 상품_등록_테스트(){
        //given
        Goods goods = Goods.builder()
            .goodsPrice(10000)
            .goodsName("사과")
            .build();

        //when
        goods = goodsRepository.saveAndFlush(goods);


        //then
        assertThat(goods.getUid()).isNotNull();
        assertThat(goods.getGoodsName()).isEqualTo("사과");
        assertThat(goods.getGoodsPrice()).isEqualTo(10000);
    }

    @Test
    @Order(2)
    void 상품_조회_테스트(){
        //when
        List<Goods> testList = goodsRepository.findAll();

        //then
        assertThat(testList).isNotNull();
        assertThat(testList.size()>0).isTrue();
    }

    @Test
    @Order(3)
    void 상품_상세_조회_테스트(){
        //when
        Goods test = goodsRepository.getById(1L);

        //then
        assertThat(test).isNotNull();
        assertThat(test.getGoodsName()).isEqualTo("사과");
    }
}