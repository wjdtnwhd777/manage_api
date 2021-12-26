package com.example.manage.customer.payload.response;

import com.example.manage.order.entity.Orders;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {

    @Schema(description = "사용자 uid")
    private long uid;
    @Schema(description = "사용자 이메일(아이디)")
    private String email;
    private List<Orders> customerOrders;

    @Builder
    public CustomerResponse(Long uid, String email, List<Orders> customerOrders){
        this.uid = uid;
        this.email = email;
        this.customerOrders = customerOrders;
    }

}
