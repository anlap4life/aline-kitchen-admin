package com.kitchen.aline.alinekitchenapp.core.contracts.repository;

import com.kitchen.aline.alinekitchenapp.domain.Food;
import com.kitchen.aline.alinekitchenapp.domain.FoodVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FoodVariantRepository extends JpaRepository<FoodVariant, Integer> {
    List<FoodVariant> findAllByFood(Food name);
}
