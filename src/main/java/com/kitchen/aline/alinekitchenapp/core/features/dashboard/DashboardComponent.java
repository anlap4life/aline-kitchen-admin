package com.kitchen.aline.alinekitchenapp.core.features.dashboard;

import java.time.LocalDate;
import java.util.List;

public interface DashboardComponent {

    Integer getProcurementTotalThisWeek(LocalDate localDate);

    Integer getOrderTotalThisWeek(LocalDate localDate);

    List<ChartDataset> getOrderChartData(LocalDate localDate);
    

}
