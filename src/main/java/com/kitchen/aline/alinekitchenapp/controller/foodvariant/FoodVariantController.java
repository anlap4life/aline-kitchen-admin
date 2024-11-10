package com.kitchen.aline.alinekitchenapp.controller.foodvariant;

import com.kitchen.aline.alinekitchenapp.controller.foodvariant.dto.CreateVariantDTO;
import com.kitchen.aline.alinekitchenapp.controller.foodvariant.dto.FoodVariantDTO;
import com.kitchen.aline.alinekitchenapp.core.features.food.FoodComponent;
import com.kitchen.aline.alinekitchenapp.core.features.foodvariant.FoodVariantComponent;
import com.kitchen.aline.alinekitchenapp.core.utils.RupiahFormatter;
import com.kitchen.aline.alinekitchenapp.domain.Food;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("foods/variants")
public class FoodVariantController {

    private final FoodComponent foodComponent;
    private final FoodVariantComponent foodVariantComponent;

    @Autowired
    public FoodVariantController(FoodComponent foodComponent, FoodVariantComponent foodVariantComponent) {
        this.foodComponent = foodComponent;
        this.foodVariantComponent = foodVariantComponent;
    }

    @GetMapping("")
    public String variantPage(@RequestParam Integer foodId, Model model) {
        Food food = foodComponent.get(new Food(foodId));
        model.addAttribute("foodId", food.getId());
        model.addAttribute("foodName", food.getName());

        List<FoodVariant> foodVariants = foodVariantComponent.getAllByFood(food);
        List<FoodVariantDTO> variantDtos = foodVariants.stream().map(variant -> {
            String formattedPrice = RupiahFormatter.format(variant.getPrice());
            return new FoodVariantDTO(variant.getId(), variant.getName(), variant.getDescription(), formattedPrice);
        }).toList();

        model.addAttribute("variants", variantDtos);

        return "variant/index";
    }

    @GetMapping("/create")
    public String createPage(@RequestParam Integer foodId, Model model) {
        Food food = foodComponent.get(new Food(foodId));
        model.addAttribute("foodId", food.getId());
        model.addAttribute("foodName", food.getName());

        model.addAttribute("variant", new CreateVariantDTO(food.getId()));
        return "variant/create";
    }

    @PostMapping("/create")
    public String createFood(@Valid @ModelAttribute("variant") CreateVariantDTO variant, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "variant/create";
        }

        FoodVariant newVariant = new FoodVariant();
        newVariant.setFood(new Food(variant.getFoodId()));
        newVariant.setName(variant.getName());
        newVariant.setDescription(variant.getDescription());
        newVariant.setPrice(variant.getPrice());

        foodVariantComponent.create(newVariant);

        redirectAttributes.addFlashAttribute("submitMessage", "Varian baru berhasil ditambahkan");
        return "redirect:/foods/variants?foodId=" + variant.getFoodId();
    }

    @GetMapping("/delete")
    public String createPage(@RequestParam Integer variantId, RedirectAttributes redirectAttributes) {
        FoodVariant variantToDelete = foodVariantComponent.get(new FoodVariant(variantId));
        Food food = variantToDelete.getFood();

        foodVariantComponent.delete(new FoodVariant(variantId));

        redirectAttributes.addFlashAttribute("submitMessage", "Varian berhasil dihapus");

        return "redirect:/foods/variants?foodId=" + food.getId();
    }

}
