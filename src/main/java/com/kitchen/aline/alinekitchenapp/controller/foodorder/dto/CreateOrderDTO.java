package com.kitchen.aline.alinekitchenapp.controller.foodorder.dto;

import com.kitchen.aline.alinekitchenapp.domain.FoodOrder;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;
import com.kitchen.aline.alinekitchenapp.domain.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CreateOrderDTO {
    private String customerName;
    private Date date;
    private List<CreateDetailOrderDTO> details = new ArrayList<>();

}
