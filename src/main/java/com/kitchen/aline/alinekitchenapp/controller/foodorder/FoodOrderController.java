package com.kitchen.aline.alinekitchenapp.controller.foodorder;

import com.kitchen.aline.alinekitchenapp.controller.foodorder.dto.CreateOrderDTO;
import com.kitchen.aline.alinekitchenapp.controller.foodorder.dto.MenuDTO;
import com.kitchen.aline.alinekitchenapp.core.features.foodvariant.FoodVariantComponent;
import com.kitchen.aline.alinekitchenapp.core.features.order.OrderComponent;
import com.kitchen.aline.alinekitchenapp.core.features.order.dto.FoodOrderDTO;
import com.kitchen.aline.alinekitchenapp.core.utils.RupiahFormatter;
import com.kitchen.aline.alinekitchenapp.domain.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("orders")
public class FoodOrderController {

    private final OrderComponent orderComponent;
    private final FoodVariantComponent foodVariantComponent;

    @Autowired
    public FoodOrderController(OrderComponent orderComponent, FoodVariantComponent foodVariantComponent) {
        this.orderComponent = orderComponent;
        this.foodVariantComponent = foodVariantComponent;
    }

    @GetMapping("/")
    public String ordersPage(Model model) {
        List<FoodOrderDTO> foodOrders = orderComponent.getFoodOrders();
        model.addAttribute("orders", foodOrders);

        return "order/index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        List<FoodVariant> foodVariants = foodVariantComponent.getAll();
        List<MenuDTO> menus = foodVariants.stream().map(menu -> {
            Food food = menu.getFood();
            String menuDescription = food.getName() + " - " + menu.getName() + "@" + RupiahFormatter.format(menu.getPrice());
            return new MenuDTO(menu.getId(), menu.getPrice(), menuDescription);
        }).toList();

        model.addAttribute("menus", menus);
        model.addAttribute("order", new CreateOrderDTO());

        return "order/create";
    }

    @ResponseBody
    @PostMapping("/create")
    public String create(@RequestBody CreateOrderDTO order) {

        FoodOrder foodOrder = FoodOrder.newOrder();
        foodOrder.setCustomerName(order.getCustomerName());
        foodOrder.setDate(order.getDate());
        foodOrder.setOrderStatus(OrderStatus.ORDERED);

        order.getDetails().forEach(detail -> {
            FoodOrderDetail foodOrderDetail = new FoodOrderDetail();
            foodOrderDetail.setFoodOrder(foodOrder);
            foodOrderDetail.setFoodVariant(new FoodVariant(detail.getVariantId()));
            foodOrderDetail.setQuantity(detail.getQuantity());
            foodOrderDetail.setPaymentSubtotal(detail.getPaymentSubtotal());

            foodOrder.addDetail(foodOrderDetail);
        });


        orderComponent.create(foodOrder);

        return "Success";
    }

    @GetMapping("/paid")
    public String pay(@RequestParam Integer orderId, RedirectAttributes redirectAttributes) {

        FoodOrder foodOrder = new FoodOrder(orderId);
        foodOrder.setOrderStatus(OrderStatus.PAID);

        orderComponent.changeStatus(foodOrder);

        redirectAttributes.addFlashAttribute("submitMessage", "Status pesanan berhasil diubah");
        return "redirect:/orders/";
    }

    @GetMapping("/delivered")
    public String deliver(@RequestParam Integer orderId, RedirectAttributes redirectAttributes) {

        FoodOrder foodOrder = new FoodOrder(orderId);
        foodOrder.setOrderStatus(OrderStatus.DELIVERED);

        orderComponent.changeStatus(foodOrder);

        redirectAttributes.addFlashAttribute("submitMessage", "Status pesanan berhasil diubah");
        return "redirect:/orders/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer orderId, RedirectAttributes redirectAttributes) {
        orderComponent.delete(new FoodOrder(orderId));

        redirectAttributes.addFlashAttribute("submitMessage", "Pesanan berhasil dihapus");
        return "redirect:/orders/";
    }

}
