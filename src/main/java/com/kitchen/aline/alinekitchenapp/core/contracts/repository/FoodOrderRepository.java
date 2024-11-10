package com.kitchen.aline.alinekitchenapp.core.contracts.repository;

import com.kitchen.aline.alinekitchenapp.domain.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {

}
