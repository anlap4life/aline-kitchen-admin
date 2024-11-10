package com.kitchen.aline.alinekitchenapp.core.contracts.repository;

import com.kitchen.aline.alinekitchenapp.domain.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, Integer> {
}
