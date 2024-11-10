package com.kitchen.aline.alinekitchenapp.controller.procurement.dto;

public record ProcurementDTO(Integer id,
                             String procurementType,
                             String name,
                             String spending,
                             String formattedDate) {
}
