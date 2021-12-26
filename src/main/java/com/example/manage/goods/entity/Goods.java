package com.example.manage.goods.entity;

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
@Table(name="GOODS")
@SequenceGenerator(
        name = "GOODS_GENERATOR",
        sequenceName = "GOODS_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_GENERATOR")
    private Long uid;

    @Column(length = 100, nullable = false)
    private String goodsName;

    private Integer goodsPrice;

    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime regDate;

    @JsonBackReference
    @OneToMany(mappedBy = "goods", fetch = FetchType.LAZY)
    private List<Orders> goodsOrders = new ArrayList<>();

    @Builder
    public Goods(Long uid, String goodsName, Integer goodsPrice, LocalDateTime regDate){
        this.uid = uid;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.regDate = regDate;
    }


}
