package com.kitchen.aline.alinekitchenapp.core.features.dashboard;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kitchen.aline.alinekitchenapp.core.contracts.repository.FoodOrderRepository;
import com.kitchen.aline.alinekitchenapp.core.contracts.repository.ProcurementRepository;
import com.kitchen.aline.alinekitchenapp.core.features.dashboard.dto.ChartDataset;
import com.kitchen.aline.alinekitchenapp.core.features.dashboard.dto.ChartDatasetColorLibrary;
import com.kitchen.aline.alinekitchenapp.domain.FoodOrder;
import com.kitchen.aline.alinekitchenapp.domain.OrderStatus;

import lombok.Getter;

@Service
@Transactional
class DashboardComponentImpl implements DashboardComponent {

    private final ProcurementRepository procurementRepository;
    private final FoodOrderRepository foodOrderRepository;

    @Autowired
    DashboardComponentImpl(ProcurementRepository procurementRepository,
            FoodOrderRepository foodOrderRepository) {
        this.procurementRepository = procurementRepository;
        this.foodOrderRepository = foodOrderRepository;
    }

    @Override
    public Integer getProcurementTotalThisWeek(LocalDate localDate) {
        QueryPeriod q = getStartAndEndOfCurrentWeek(tranformLocalDateToDate(localDate));
        return procurementRepository.sumProcurements(q.startDate, q.endDate);
    }

    @Override
    public Integer getOrderTotalThisWeek(LocalDate localDate) {
        QueryPeriod q = getStartAndEndOfCurrentWeek(tranformLocalDateToDate(localDate));
        return foodOrderRepository.sumOrderByPeriod(q.startDate, q.endDate);
    }

    private QueryPeriod getStartAndEndOfCurrentWeek(Date date) {
        // 1. Create a Calendar instance and set the date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 2. Set the calendar to the start of the week (Monday)
        // Calendar.DAY_OF_WEEK starts at 1 (Sunday), so we need to adjust for Monday
        // (2)
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int diffToMonday = (dayOfWeek == Calendar.SUNDAY) ? -6 : Calendar.MONDAY - dayOfWeek;

        // Adjust calendar to the start of the week (Monday)
        calendar.add(Calendar.DAY_OF_WEEK, diffToMonday);

        // Subtract one day
        // untuk kebutuhan query
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // 3. Get the start of the week (Monday)
        Date startOfWeek = calendar.getTime();
        System.out.println("Start of the week: " + startOfWeek);

        // 4. Calculate the end of the week (Sunday) by adding 6 days to the start date
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date endOfWeek = calendar.getTime();
        System.out.println("Start of the week: " + endOfWeek);

        return new QueryPeriod(startOfWeek, endOfWeek);
    }

    @Override
    public List<ChartDataset> getOrderChartData(LocalDate localDate) {
        List<Long> orderWithOrderedStatus = new ArrayList<>();
        List<Long> orderWithPaidStatus = new ArrayList<>();
        List<Long> orderWithDelivered = new ArrayList<>();

        List<Date> dayOfTheWeek = getAllDayInAWeek(localDate);
        dayOfTheWeek.forEach(day -> {
            List<FoodOrder> orders = foodOrderRepository.findAllByDate(day);
            long countOrdered = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(OrderStatus.ORDERED))
                    .count();
            orderWithOrderedStatus.add(countOrdered);

            long countPaid = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(OrderStatus.PAID))
                    .count();
            orderWithPaidStatus.add(countPaid);

            long countDelivered = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(OrderStatus.DELIVERED))
                    .count();
            orderWithDelivered.add(countDelivered);
        });

        ChartDataset orderedStatusDataset = new ChartDataset();
        orderedStatusDataset.setLabel("Belum Bayar");
        orderedStatusDataset.setData(orderWithOrderedStatus.stream().map(l -> l.intValue())
                .toArray(Integer[]::new));
        orderedStatusDataset.setBackgroundColor(ChartDatasetColorLibrary.RED_1());

        ChartDataset paidStatusDataset = new ChartDataset();
        paidStatusDataset.setLabel("Sudah Bayar");
        paidStatusDataset.setData(orderWithPaidStatus.stream().map(l -> l.intValue())
                .toArray(Integer[]::new));
        paidStatusDataset.setBackgroundColor(ChartDatasetColorLibrary.RED_2());

        ChartDataset deliveredStatusDataset = new ChartDataset();
        deliveredStatusDataset.setLabel("Sudah Diantar");
        deliveredStatusDataset.setData(orderWithDelivered.stream().map(l -> l.intValue())
                .toArray(Integer[]::new));
        deliveredStatusDataset.setBackgroundColor(ChartDatasetColorLibrary.RED_3());

        return List.of(orderedStatusDataset, paidStatusDataset, deliveredStatusDataset);
    }

    private List<Date> getAllDayInAWeek(LocalDate localDate) {
       
        // Get the current week's start (Monday) and end (Sunday)
        LocalDate startOfWeek = localDate.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = localDate.with(DayOfWeek.SUNDAY);

        // List to store the days of the current week
        List<Date> weekDays = new ArrayList<>();

        // Loop through the days of the current week
        LocalDate currentDay = startOfWeek;
        while (!currentDay.isAfter(endOfWeek)) {
            // currentDay.format(formatter)
            Date date = tranformLocalDateToDate(currentDay);
            weekDays.add(date);
            currentDay = currentDay.plusDays(1);

            System.out.println(date.toString());
        }

   

        return weekDays;
    }

    private Date tranformLocalDateToDate(LocalDate localDate) {
        // Convert LocalDate to Instant at the start of the day (midnight)
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        // Convert Instant to java.util.Date
        return Date.from(instant);
    }

    @Getter
    static class QueryPeriod {
        private Date startDate;
        private Date endDate;

        public QueryPeriod(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

}
