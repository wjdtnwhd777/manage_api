package com.example.manage.order.repository;

import com.example.manage.customer.entity.Customer;
import com.example.manage.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByCustomer(Customer customer);
}
