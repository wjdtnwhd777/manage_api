package com.example.manage.customer.entity;

import com.example.manage.order.entity.Orders;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="CUSTOMER")
@SequenceGenerator(
        name = "CUSTOMER_GENERATOR",
        sequenceName = "CUSTOMER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_GENERATOR")
    @Column(name = "uid")
    private Long uid;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @JsonBackReference
    @Column(name = "passwd", length = 255, nullable = false)
    private String passwd;

    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime regDate;

    @JsonBackReference
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Orders> customerOrders = new ArrayList<>();

    @Builder
    public Customer(Long uid, String email, String passwd, LocalDateTime regDate){
        this.uid = uid;
        this.email = email;
        this.passwd = passwd;
        this.regDate = regDate;
    }

}
