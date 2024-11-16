package com.kitchen.aline.alinekitchenapp.core.contracts.repository;

import com.kitchen.aline.alinekitchenapp.domain.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {

    @Query("select sum(a.paymentTotal) from FoodOrder a where a.date between ?1 and ?2")
    Integer sumOrderByPeriod(Date startDate, Date endDate);

    @Query("select a from FoodOrder a where date(a.date)  = ?1")
    List<FoodOrder> findAllByDate(Date date);
}
