package com.example.manage.goods.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostGoodsRequest {

    @Schema(description = "등록할 상품명")
    @NotEmpty(message = "상품명은 필수입니다.")
    private String goodsName;

    @Schema(description = "등록할 상품 금액")
    @NotNull(message = "상품금액은 필수입니다.")
    private Integer goodsPrice;

}
