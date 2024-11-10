package com.kitchen.aline.alinekitchenapp.controller.food;

import com.kitchen.aline.alinekitchenapp.controller.food.dto.CreateFoodDTO;
import com.kitchen.aline.alinekitchenapp.controller.food.dto.FoodDTO;
import com.kitchen.aline.alinekitchenapp.core.features.food.FoodComponent;
import com.kitchen.aline.alinekitchenapp.domain.Food;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("foods")
public class FoodController {

    private final FoodComponent foodComponent;

    @Autowired
    public FoodController(FoodComponent foodComponent) {
        this.foodComponent = foodComponent;
    }

    @GetMapping("/")
    public String foodsPage(Model model) {
        List<Food> foods = foodComponent.getAllFoods();
        List<FoodDTO> foodDtos = foods.stream().map(food -> new FoodDTO(food.getId().toString(), food.getName(), food.getDescription())).toList();
        model.addAttribute("foods", foodDtos);

        return "food/index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("food", new CreateFoodDTO());
        return "food/create";
    }

    @PostMapping("/create")
    public String createFood(@Valid @ModelAttribute("food") CreateFoodDTO food, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "food/create";
        }

        Food newFood = new Food();
        newFood.setName(food.getName());
        newFood.setDescription(food.getDescription());

        foodComponent.create(newFood);

        redirectAttributes.addFlashAttribute("submitMessage", "Makanan baru berhasil ditambahkan");
        return "redirect:/foods/";
    }

    @GetMapping("/delete")
    public String deleteFood(@RequestParam Integer foodId, RedirectAttributes redirectAttributes) {
        foodComponent.delete(new Food(foodId));
        redirectAttributes.addFlashAttribute("submitMessage", "Makanan berhasil dihapus");

        return "redirect:/foods/";
    }

}
