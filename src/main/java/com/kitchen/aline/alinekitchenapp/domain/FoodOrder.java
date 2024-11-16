package com.kitchen.aline.alinekitchenapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "FOOD_ORDER")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS")
    private OrderStatus orderStatus;

    @Column(name = "PAYMENT_TOTAL")
    private Integer paymentTotal;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "foodOrder")
    private List<FoodOrderDetail> details = new ArrayList<>();

    public FoodOrder(Integer id) {
        this.id = id;
    }

    public FoodOrder(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addDetail(FoodOrderDetail foodOrderDetail) {
        this.getDetails().add(foodOrderDetail);

        Integer currentPayment = this.getPaymentTotal();
        currentPayment += foodOrderDetail.getPaymentSubtotal();
        this.setPaymentTotal(currentPayment);

    }

    public static FoodOrder newOrder()  {
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setPaymentTotal(0);
        return foodOrder;
    }
}
