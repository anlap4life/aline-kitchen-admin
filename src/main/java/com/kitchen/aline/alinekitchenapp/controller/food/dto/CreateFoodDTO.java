package com.kitchen.aline.alinekitchenapp.controller.food.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//you can check on beanvalidation.org
@Data
public class CreateFoodDTO {

    @NotBlank(message = "Isian tidak boleh kosong")
    @Size(min = 5, message = "Minimal 5 karakter")
    private String name;

    @NotBlank(message = "Isian tidak boleh kosong")
    @Size(min = 10, message = "Minimal 10 karakter")
    private String description;
}
