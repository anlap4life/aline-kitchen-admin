package com.kitchen.aline.alinekitchenapp.controller.procurement;

import com.kitchen.aline.alinekitchenapp.controller.procurement.dto.CreateProcurementDTO;
import com.kitchen.aline.alinekitchenapp.controller.procurement.dto.ProcurementDTO;
import com.kitchen.aline.alinekitchenapp.core.features.procurement.ProcurementComponent;
import com.kitchen.aline.alinekitchenapp.core.utils.DateFormatter;
import com.kitchen.aline.alinekitchenapp.core.utils.RupiahFormatter;
import com.kitchen.aline.alinekitchenapp.domain.Procurement;
import com.kitchen.aline.alinekitchenapp.domain.ProcurementType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("procurements")
public class ProcurementController {

    private final ProcurementComponent procurementComponent;

    @Autowired
    public ProcurementController(ProcurementComponent procurementComponent) {
        this.procurementComponent = procurementComponent;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/")
    public String procurementPage(Model model) {
        List<Procurement> procurements = procurementComponent.getAll();
        List<ProcurementDTO> procurementDTOS = procurements.stream().map(procurement -> new ProcurementDTO(procurement.getId(),
                procurement.getProcurementType().getValue(),
                procurement.getName(),
                RupiahFormatter.format(procurement.getSpending()),
                DateFormatter.format (procurement.getDate()))).toList();

        model.addAttribute("procurements", procurementDTOS);

        return "procurement/index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        ProcurementType[] procurementTypes = ProcurementType.values();
        model.addAttribute("procurementTypes", procurementTypes);

        model.addAttribute("procurement", new CreateProcurementDTO());

        return "procurement/create";
    }

    @PostMapping("/create")
    public String createFood(@Valid @ModelAttribute("procurement") CreateProcurementDTO procurement,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "procurement/create";
        }

        Procurement newProcurement = new Procurement();
        newProcurement.setProcurementType(procurement.getProcurementType());
        newProcurement.setName(procurement.getName());
        newProcurement.setSpending(procurement.getSpending());
        newProcurement.setDate(procurement.getDate());

        procurementComponent.create(newProcurement);

        redirectAttributes.addFlashAttribute("submitMessage", "Belanja baru berhasil ditambahkan");
        return "redirect:/procurements/";
    }

    @GetMapping("/delete")
    public String deleteFood(@RequestParam Integer procurementId, RedirectAttributes redirectAttributes) {
        procurementComponent.delete(new Procurement(procurementId));
        redirectAttributes.addFlashAttribute("submitMessage", "Belanja berhasil dihapus");

        return "redirect:/procurements/";
    }

}
