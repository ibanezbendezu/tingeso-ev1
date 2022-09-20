package cl.tingeso.mueblesstgo.controllers;

import cl.tingeso.mueblesstgo.services.EmployeeService;
import cl.tingeso.mueblesstgo.services.WageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("wage")
public class WageController {

    @Autowired
    WageService wageService;
    @Autowired
    EmployeeService employeeService;

    @GetMapping("{id}")
    public String showById(@PathVariable Long id, Model model){
        model.addAttribute("wage", wageService.getById(id, Boolean.TRUE));
        return "pages/wage";
    }

    @GetMapping("/search-wage")
    public String searchByRut(@RequestParam("rut") String rut){
        Long id = employeeService.obtenerPorRut(rut).getId();
        return "redirect:/wage/" + id;
    }
}
