package com.example.manage.goods.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetGoodsRequest {

    @Schema(description = "상세 조회할 상품 uid")
    private Long uid;

}
