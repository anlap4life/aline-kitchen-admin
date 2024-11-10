package com.kitchen.aline.alinekitchenapp.controller.foodvariant.dto;

import com.kitchen.aline.alinekitchenapp.domain.Food;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateVariantDTO {

    @NotNull(message = "Invalid FOOD ID")
    private Integer foodId;

    @NotBlank(message = "Isian tidak boleh kosong")
    @Size(min = 5, message = "Minimal 5 karakter")
    private String name;

    private String description;

    @NotNull(message = "Isian tidak boleh kosong")
    @Min(value = 500, message = "Harga minimal 500 rupiah")
    private Integer price;

    public CreateVariantDTO(Integer foodId) {
        this.foodId = foodId;
    }
}
