package com.kitchen.aline.alinekitchenapp.core.features.dashboard;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import com.kitchen.aline.alinekitchenapp.core.contracts.repository.FoodOrderRepository;
import com.kitchen.aline.alinekitchenapp.core.contracts.repository.ProcurementRepository;
import com.kitchen.aline.alinekitchenapp.domain.FoodOrder;
import com.kitchen.aline.alinekitchenapp.domain.OrderStatus;

@ExtendWith(MockitoExtension.class)
public class DashboardComponentImplTest {

    DashboardComponentImpl underTest;

    @Mock
    ProcurementRepository procurementRepository;

    @Mock
    FoodOrderRepository foodOrderRepository;

    @BeforeEach
    void init() {
        underTest = new DashboardComponentImpl(procurementRepository, foodOrderRepository);
    }

    @Test
    void smokeTest() {
        System.out.println("Smoking hot!");
    }

    @Test
    void berhasliGenerateSesuaiDenganDataYangAda() {
        // GIVEN
        LocalDate givenDate = LocalDate.of(2024, 11, 15);

        List<FoodOrder> ordersDay1 = List.of(new FoodOrder(OrderStatus.PAID),
                new FoodOrder(OrderStatus.ORDERED),
                new FoodOrder(OrderStatus.DELIVERED));

        List<FoodOrder> ordersDay2 = List.of(new FoodOrder(OrderStatus.ORDERED));

        List<FoodOrder> ordersDay3 = List.of(new FoodOrder(OrderStatus.DELIVERED));

        List<FoodOrder> ordersDay4 = List.of(new FoodOrder(OrderStatus.DELIVERED),
                new FoodOrder(OrderStatus.DELIVERED));

        List<FoodOrder> ordersDay5 = List.of(new FoodOrder(OrderStatus.PAID));

        List<FoodOrder> ordersDay6 = List.of();

        List<FoodOrder> ordersDay7 = List.of();

        when(foodOrderRepository.findAllByDate(any())).thenReturn(ordersDay1,
                ordersDay2,
                ordersDay3,
                ordersDay4,
                ordersDay5,
                ordersDay6,
                ordersDay7);

        // WHEN
        List<ChartDataset> expected = underTest.getOrderChartData(givenDate);

        // THEN
        assertThat(expected.size()).isEqualTo(3);
    }
}
