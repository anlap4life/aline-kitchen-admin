package com.kitchen.aline.alinekitchenapp.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDERED("Sudah Pesan"),
    PAID("Sudah Bayar"),
    DELIVERED("Sudah Diantar");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
