package com.example.manage.order.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostOrdersRequest {

    @Schema(description = "주문할 상품 정보")
    @NotNull(message = "상품 선택은 필수입니다.")
    private long goodsUid;

}
