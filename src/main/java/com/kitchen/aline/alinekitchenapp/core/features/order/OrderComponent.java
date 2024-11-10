package com.kitchen.aline.alinekitchenapp.core.features.order;

import com.kitchen.aline.alinekitchenapp.core.features.order.dto.FoodOrderDTO;
import com.kitchen.aline.alinekitchenapp.domain.FoodOrder;

import java.util.List;

public interface OrderComponent {
    List<FoodOrderDTO> getFoodOrders();

    FoodOrder create(FoodOrder foodOrder);

    FoodOrder get(FoodOrder foodOrder);

    void delete(FoodOrder foodOrder);

    void changeStatus(FoodOrder foodOrder);
}
