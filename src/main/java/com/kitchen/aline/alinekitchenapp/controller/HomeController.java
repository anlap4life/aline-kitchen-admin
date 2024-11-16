package com.kitchen.aline.alinekitchenapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kitchen.aline.alinekitchenapp.core.features.dashboard.DashboardComponent;
import com.kitchen.aline.alinekitchenapp.core.utils.RupiahFormatter;

@Controller
public class HomeController {

    private final DashboardComponent dashboardComponent;

    @Autowired
    public HomeController(DashboardComponent dashboardComponent) {
        this.dashboardComponent = dashboardComponent;
    }

    @GetMapping("/")
    public String home(Model model) {
        LocalDate today = LocalDate.now();
        Integer totalProcurement = dashboardComponent.getProcurementTotalThisWeek(today);
        model.addAttribute("totalProcurement", RupiahFormatter.format(totalProcurement));

        Integer totalOrder = dashboardComponent.getOrderTotalThisWeek(today);
        model.addAttribute("totalOrder", RupiahFormatter.format(totalOrder));

        Integer netIncome = totalOrder - totalProcurement;
        model.addAttribute("netIncome", RupiahFormatter.format(netIncome));

        return "index";
    }
}
