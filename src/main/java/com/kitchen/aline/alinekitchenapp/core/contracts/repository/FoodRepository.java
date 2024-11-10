package com.kitchen.aline.alinekitchenapp.core.contracts.repository;

import com.kitchen.aline.alinekitchenapp.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
}
