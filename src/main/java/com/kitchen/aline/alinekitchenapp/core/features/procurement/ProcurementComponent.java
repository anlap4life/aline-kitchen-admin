package com.kitchen.aline.alinekitchenapp.core.features.procurement;

import com.kitchen.aline.alinekitchenapp.domain.Procurement;

import java.util.List;

public interface ProcurementComponent {

    List<Procurement> getAll();

    Procurement create(Procurement procurement);

    void delete(Procurement procurement);

}
