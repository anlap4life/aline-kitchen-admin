package com.kitchen.aline.alinekitchenapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "FOOD_ORDER_DETAIL")
public class FoodOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ORDER_ID")
    private FoodOrder foodOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_VARIANT_ID")
    private FoodVariant foodVariant;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PAYMENT_SUBTOTAL")
    private Integer paymentSubtotal;

    public FoodOrderDetail(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }
}
