package com.example.manage.order.entity;

import com.example.manage.customer.entity.Customer;
import com.example.manage.goods.entity.Goods;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Entity
@Data
@Table(name="ORDERS")
@SequenceGenerator(
        name = "ORDERS_GENERATOR",
        sequenceName = "ORDERS_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_GENERATOR")
    private Long uid;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Goods goods;

    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime regDate;

    @Builder
    public Orders(Long uid, Customer customer, Goods goods, LocalDateTime regDate){
        this.uid = uid;
        this.customer = customer;
        this.goods = goods;
        this.regDate = regDate;
    }
}
