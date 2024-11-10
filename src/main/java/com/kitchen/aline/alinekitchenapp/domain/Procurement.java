package com.kitchen.aline.alinekitchenapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PROCUREMENT")
public class Procurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROCUREMENT_TYPE")
    private ProcurementType procurementType;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SPENDING")
    private Integer spending;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    public Procurement(Integer id) {
        this.id = id;
    }
}
