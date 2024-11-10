package com.kitchen.aline.alinekitchenapp.core.features.foodvariant;

import com.kitchen.aline.alinekitchenapp.core.contracts.repository.FoodRepository;
import com.kitchen.aline.alinekitchenapp.core.contracts.repository.FoodVariantRepository;
import com.kitchen.aline.alinekitchenapp.domain.Food;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
class FoodVariantComponentImpl implements FoodVariantComponent {

    private final FoodVariantRepository foodVariantRepository;

    @Autowired
    FoodVariantComponentImpl(FoodVariantRepository foodVariantRepository) {
        this.foodVariantRepository = foodVariantRepository;
    }

    @Override
    public List<FoodVariant> getAllByFood(Food food) {
        return foodVariantRepository.findAllByFood(food);
    }

    @Override
    public FoodVariant create(FoodVariant foodVariant) {
        return foodVariantRepository.save(foodVariant);
    }

    @Override
    public void delete(FoodVariant foodVariant) {
        foodVariantRepository.findById(foodVariant.getId()).ifPresent(foodVariantRepository::delete);
    }

    @Override
    public FoodVariant get(FoodVariant foodVariant) {
        return foodVariantRepository.findById(foodVariant.getId())
                .orElseThrow(() -> new RuntimeException("Food Variant not found"));
    }

    @Override
    public List<FoodVariant> getAll() {
        return foodVariantRepository.findAll().stream()
                .sorted(Comparator.comparing(o -> o.getFood().getName()))
                .toList();

    }
}
