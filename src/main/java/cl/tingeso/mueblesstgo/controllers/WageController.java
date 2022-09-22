package cl.tingeso.mueblesstgo.controllers;

import cl.tingeso.mueblesstgo.services.WageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("wage")
public class WageController {

    private final WageService wageService;

    public WageController(WageService wageService) {
        this.wageService = wageService;
    }

    @GetMapping("{id}")
    public String showById(@PathVariable Long id, Model model){
        model.addAttribute("wage", wageService.getById(id, Boolean.TRUE));
        return "pages/wage";
    }

    @GetMapping("/search")
    public String getWageSearch() { return "pages/search-wage"; }

    @GetMapping("/find")
    public String searchByRutAndDate(@RequestParam("rut") String rut,
                                     @RequestParam("month") String month, Model model) {
        try {
            Long id = wageService.findByEmployeeIdAndDate(rut, month);
            return "redirect:" + id;
        } catch (Exception e) {
            model.addAttribute("error", "El sueldo buscado no se encuentra registrado.");
            return "pages/search-wage";
        }
    }
}
