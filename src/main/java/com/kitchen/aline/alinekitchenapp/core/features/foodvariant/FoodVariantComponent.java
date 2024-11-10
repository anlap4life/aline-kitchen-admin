package com.kitchen.aline.alinekitchenapp.core.features.foodvariant;

import com.kitchen.aline.alinekitchenapp.domain.Food;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;

import java.util.List;

public interface FoodVariantComponent {

    List<FoodVariant> getAllByFood(Food food);

    FoodVariant create(FoodVariant foodVariant);

    void delete(FoodVariant foodVariant);

    FoodVariant get(FoodVariant foodVariant);

    List<FoodVariant> getAll();
}
