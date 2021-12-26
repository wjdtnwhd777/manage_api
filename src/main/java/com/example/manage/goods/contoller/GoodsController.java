package com.example.manage.goods.contoller;

import com.example.manage.core.ResultCodes;
import com.example.manage.core.exception.CustomException;
import com.example.manage.goods.entity.Goods;
import com.example.manage.goods.payload.request.GetGoodsRequest;
import com.example.manage.goods.payload.request.PostGoodsRequest;
import com.example.manage.goods.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {

    private final GoodsService goodsService;

    @Operation(summary = "상품 목록 불러오기", description = "등록된 상품 목록을 불러옵니다.")
    @GetMapping()
    public ResponseEntity<?> getGoods(){
        List<Goods> result = goodsService.goodsList();

        if(result.size() > 0) {
            return ResponseEntity.ok(result);
        }
        else {
            throw new CustomException(ResultCodes.GOODS_NOT_FOUND_EXCEPTION);
        }
    }

    @Operation(summary = "상품 정보 가져오기", description = "등록된 상품 정보를 불러옵니다.")
    @GetMapping("/{uid}")
    public ResponseEntity<?> getGoods(@PathVariable Long uid){
        Goods result = goodsService.getGoods(uid);

        if(result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            throw new CustomException(ResultCodes.GOODS_NOT_FOUND_EXCEPTION);
        }
    }

    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    @PostMapping()
    public ResponseEntity<?> postGoods(@RequestBody @Valid PostGoodsRequest request){
        Goods result = goodsService.postGoods(request);
        if(result.getUid() != null){
            return ResponseEntity.ok(result);
        }
        else {
            throw new CustomException(ResultCodes.GOODS_NOT_REGISTER_EXCEPTION);
        }
    }


}
