package com.example.manage.order.contoller;


import com.example.manage.core.ResultCodes;
import com.example.manage.core.exception.CustomException;
import com.example.manage.order.entity.Orders;
import com.example.manage.order.payload.request.PostOrdersRequest;
import com.example.manage.order.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Slf4j
public class OrdersController {

    private final OrdersService ordersService;

    @Operation(summary = "주문 상품 목록 가져오기", description = "로그인한 사용자가 주문한 상품 목록을 가져옵니다.")
    @GetMapping()
    public ResponseEntity<?> getOrders(HttpServletRequest request) throws Exception {
        List<Orders> result = ordersService.ordersList(request);

        if(result.size() > 0) {
            return ResponseEntity.ok(result);
        }
        else {
            throw new CustomException(ResultCodes.ORDER_NOT_FOUND_EXCEPTION);
        }
    }

    @Operation(summary = "주문정보 상세 가져오기", description = "주문한 제품의 상세 정보를 보여줍니다.")
    @GetMapping("/{uid}")
    public ResponseEntity<?> getOrders(@PathVariable Long uid){
        return ResponseEntity.ok(ordersService.getOrders(uid));
    }

    @Operation(summary = "주문하기", description = "로그인한 사용자와 상품정보를 받아 주문목록에 등록합니다.")
    @PostMapping()
    public ResponseEntity<?> postOrders(@RequestBody @Valid PostOrdersRequest postOrdersRequest, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(ordersService.postOrders(postOrdersRequest, request));
    }

}
