package com.kitchen.aline.alinekitchenapp.core.features.food;

import com.kitchen.aline.alinekitchenapp.domain.Food;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;

import java.util.List;

public interface FoodComponent {

    List<Food> getAllFoods();

    Food create(Food food);

    void delete(Food food);

    Food get(Food food);
}
