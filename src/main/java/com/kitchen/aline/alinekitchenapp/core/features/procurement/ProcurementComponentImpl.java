package com.kitchen.aline.alinekitchenapp.core.features.procurement;

import com.kitchen.aline.alinekitchenapp.core.contracts.repository.ProcurementRepository;
import com.kitchen.aline.alinekitchenapp.domain.Procurement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
class ProcurementComponentImpl implements ProcurementComponent {

    private final ProcurementRepository procurementRepository;

    @Autowired
    ProcurementComponentImpl(ProcurementRepository procurementRepository) {
        this.procurementRepository = procurementRepository;
    }

    @Override
    public List<Procurement> getAll() {
        return procurementRepository.findAll();
    }

    @Override
    public Procurement create(Procurement procurement) {
        return procurementRepository.save(procurement);
    }

    @Override
    public void delete(Procurement procurement) {
        procurementRepository.findById(procurement.getId())
                .ifPresent(procurementRepository::delete);
    }
}
