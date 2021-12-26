package com.example.manage.order.service;

import com.example.manage.core.ResultCodes;
import com.example.manage.core.encryption.Crypt;
import com.example.manage.core.exception.CustomException;
import com.example.manage.core.security.jwt.JwtUtils;
import com.example.manage.customer.entity.Customer;
import com.example.manage.customer.payload.response.CustomerResponse;
import com.example.manage.customer.service.CustomerService;
import com.example.manage.goods.service.GoodsService;
import com.example.manage.order.entity.Orders;
import com.example.manage.order.payload.request.PostOrdersRequest;
import com.example.manage.order.repository.OrdersRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final CustomerService customerService;
    private final GoodsService goodsService;
    private final JwtUtils jwtUtils;

    public List<Orders> ordersList(HttpServletRequest request) throws Exception {
        return ordersRepository.findByCustomer(parseHeader(request));
    }

    public Orders postOrders(PostOrdersRequest postOrdersRequest, HttpServletRequest request) throws Exception {

        return ordersRepository.saveAndFlush(
                Orders.builder()
                        .customer(parseHeader(request))
                        .goods(goodsService.getGoods(postOrdersRequest.getGoodsUid()))
                        .build()
        );
    }

    public Orders getOrders(Long uid) {
        return ordersRepository.findById(uid).orElseThrow(() -> new CustomException(ResultCodes.ORDER_NOT_FOUND_EXCEPTION));
    }

    public Customer parseHeader(HttpServletRequest request) throws Exception {
        Map<String, Object> claims = jwtUtils.getClaimsFromJwtToken(jwtUtils.parseJwt(request));
        String claimJsonString = MapUtils.getString(claims, "data");
        JsonElement e = JsonParser.parseString(Crypt.decrypt(claimJsonString));
        JsonObject claimJson = e.getAsJsonObject();

        CustomerResponse customerResponse = customerService.getCustomerFromEmail(claimJson.get("email").getAsString());

        return Customer.builder()
                .uid(customerResponse.getUid())
                .email(customerResponse.getEmail())
                .build();
    }
}
