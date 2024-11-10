package com.kitchen.aline.alinekitchenapp.core.features.order;

import com.kitchen.aline.alinekitchenapp.core.contracts.repository.FoodOrderRepository;
import com.kitchen.aline.alinekitchenapp.core.features.order.dto.FoodOrderDTO;
import com.kitchen.aline.alinekitchenapp.core.utils.DateFormatter;
import com.kitchen.aline.alinekitchenapp.core.utils.RupiahFormatter;
import com.kitchen.aline.alinekitchenapp.domain.Food;
import com.kitchen.aline.alinekitchenapp.domain.FoodOrder;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
class OrderComponentImpl implements OrderComponent {

    private final FoodOrderRepository foodOrderRepository;

    @Autowired
    OrderComponentImpl(FoodOrderRepository foodOrderRepository) {
        this.foodOrderRepository = foodOrderRepository;
    }

    @Override
    public List<FoodOrderDTO> getFoodOrders() {
        return foodOrderRepository.findAll()
                .stream().sorted(Comparator.comparing(FoodOrder::getDate))
                .map(foodOrder -> {
                    FoodOrderDTO foodOrderDTO = new FoodOrderDTO();
                    foodOrderDTO.setId(foodOrder.getId());
                    foodOrderDTO.setCustomerName(foodOrder.getCustomerName());
                    foodOrderDTO.setDate(DateFormatter.format(foodOrder.getDate()));
                    foodOrderDTO.setOrderStatus(foodOrder.getOrderStatus().getValue());
                    foodOrderDTO.setPaymentTotal(RupiahFormatter.format(foodOrder.getPaymentTotal()));

                    foodOrder.getDetails().forEach(detail -> {
                        FoodOrderDTO.FoodOrderDetailDTO detailDTO = new FoodOrderDTO.FoodOrderDetailDTO();
                        detailDTO.setId(detail.getId());
                        detailDTO.setQuantity(detail.getQuantity());
                        detailDTO.setPaymentSubtotal(RupiahFormatter.format(detail.getPaymentSubtotal()));

                        FoodVariant foodVariant = detail.getFoodVariant();
                        detailDTO.setVariantName(foodVariant.getName());

                        Food food = foodVariant.getFood();
                        detailDTO.setFoodName(food.getName());

                        foodOrderDTO.getOrderDetails().add(detailDTO);
                    });

                    return foodOrderDTO;
                }).toList();
    }

    @Override
    public FoodOrder create(FoodOrder foodOrder) {
        return foodOrderRepository.save(foodOrder);
    }

    @Override
    public FoodOrder get(FoodOrder foodOrder) {
        return foodOrderRepository.findById(foodOrder.getId())
                .orElseThrow(() -> new RuntimeException("Food Order not found"));
    }

    @Override
    public void delete(FoodOrder foodOrder) {
        foodOrderRepository.findById(foodOrder.getId()).ifPresent(foodOrderRepository::delete);
    }

    @Override
    public void changeStatus(FoodOrder foodOrder) {
        FoodOrder orderToChange = this.get(foodOrder);
        orderToChange.setOrderStatus(foodOrder.getOrderStatus());
        foodOrderRepository.save(orderToChange);
    }
}
