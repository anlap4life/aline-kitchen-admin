package com.kitchen.aline.alinekitchenapp.controller.foodorder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDetailOrderDTO {
    private Integer variantId;
    private Integer quantity;
    private Integer paymentSubtotal;
}
