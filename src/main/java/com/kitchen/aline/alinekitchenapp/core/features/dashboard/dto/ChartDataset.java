package com.kitchen.aline.alinekitchenapp.core.features.dashboard.dto;

import lombok.Data;

@Data
public class ChartDataset {
    private String label;
    private Integer[] data;
    private Integer borderWidth = 1;
    private String backgroundColor;
}
