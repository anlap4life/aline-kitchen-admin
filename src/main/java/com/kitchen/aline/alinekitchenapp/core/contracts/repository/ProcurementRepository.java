package com.kitchen.aline.alinekitchenapp.core.contracts.repository;

import com.kitchen.aline.alinekitchenapp.domain.Procurement;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, Integer> {

    @Query("select sum(a.spending) from Procurement a where a.date between ?1 and ?2")
    Integer sumProcurements(Date startDate, Date endDate);

}
