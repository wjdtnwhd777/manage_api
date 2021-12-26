package com.example.manage.customer.contoller;

import com.example.manage.customer.payload.request.CustomerRequest;
import com.example.manage.customer.payload.response.CustomerResponse;
import com.example.manage.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 사용자 등록
     * @param customerRequest
     * @return customerResponse
     * @throws Exception
     */
    @Operation(summary="사용자 등록", description = "CustomerRequest를 통해 email, password를 전달받아 사용자를 등록한다")
    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<?> joinCustomer(@RequestBody @Valid CustomerRequest customerRequest) throws Exception {

        CustomerResponse response = customerService.joinCustomer(customerRequest);

        if(response == null){
            throw new Exception("사용자 등록 실패");
        }

        return ResponseEntity.ok(response);
    }

}
