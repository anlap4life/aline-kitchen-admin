package com.kitchen.aline.alinekitchenapp.core.features.food;

import com.kitchen.aline.alinekitchenapp.core.contracts.repository.FoodRepository;
import com.kitchen.aline.alinekitchenapp.core.contracts.repository.FoodVariantRepository;
import com.kitchen.aline.alinekitchenapp.domain.Food;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
class FoodComponentImpl implements FoodComponent {

    private final FoodRepository foodRepository;

    @Autowired
    FoodComponentImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food create(Food food) {
       return foodRepository.save(food);
    }

    @Override
    public void delete(Food food) {
        foodRepository.findById(food.getId()).ifPresent(foodRepository::delete);
    }

    @Override
    public Food get(Food food) {
        return foodRepository.findById(food.getId())
                .orElseThrow(() -> new RuntimeException("Food not found"));
    }
}
