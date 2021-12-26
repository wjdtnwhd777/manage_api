package com.example.manage.goods.service;

import com.example.manage.core.ResultCodes;
import com.example.manage.core.exception.CustomException;
import com.example.manage.goods.entity.Goods;
import com.example.manage.goods.payload.request.PostGoodsRequest;
import com.example.manage.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public List<Goods> goodsList() {
        return goodsRepository.findAll();
    }

    public Goods postGoods(PostGoodsRequest request) {
        return goodsRepository.saveAndFlush(
                Goods.builder()
                    .goodsName(request.getGoodsName())
                    .goodsPrice(request.getGoodsPrice())
                    .build()
        );
    }

    public Goods getGoods(Long uid){
        return goodsRepository.findById(uid).orElseThrow(()-> new CustomException(ResultCodes.GOODS_NOT_FOUND_EXCEPTION));
    }
}
