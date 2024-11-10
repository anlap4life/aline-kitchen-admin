package com.kitchen.aline.alinekitchenapp.core.features.order.dto;

import com.kitchen.aline.alinekitchenapp.domain.FoodOrder;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;
import com.kitchen.aline.alinekitchenapp.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class FoodOrderDTO {

    private Integer id;
    private String customerName;
    private String date;
    private String orderStatus;
    private String paymentTotal;
    private List<FoodOrderDetailDTO> orderDetails = new ArrayList<>();

    @Data
    public static class FoodOrderDetailDTO {
        private Integer id;
        private String foodName;
        private String variantName;
        private Integer quantity;
        private String paymentSubtotal;
    }

}
