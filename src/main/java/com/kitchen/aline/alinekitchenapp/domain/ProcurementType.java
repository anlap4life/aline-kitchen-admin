package com.kitchen.aline.alinekitchenapp.domain;

import lombok.Getter;

@Getter
public enum ProcurementType {
    INGREDIENT("Bahan makanan"),
    OTHER("Lainnya");

    private final String value;

    ProcurementType(String value) {
        this.value = value;
    }
}
