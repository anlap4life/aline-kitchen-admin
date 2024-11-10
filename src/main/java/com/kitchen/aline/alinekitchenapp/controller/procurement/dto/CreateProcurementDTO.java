package com.kitchen.aline.alinekitchenapp.controller.procurement.dto;

import com.kitchen.aline.alinekitchenapp.domain.ProcurementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CreateProcurementDTO {

    @NotNull(message = "Isian tidak boleh kosong")
    private ProcurementType procurementType;

    @NotBlank(message = "Isian tidak boleh kosong")
    private String name;

    @NotNull(message = "Isian tidak boleh kosong")
    private Integer spending;

    @NotNull(message = "Isian tidak boleh kosong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
