package cl.tingeso.mueblesstgo.controllers;

import cl.tingeso.mueblesstgo.controllers.model.WageVo;
import cl.tingeso.mueblesstgo.services.WageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public String showWages(Model model) {
        try {
            List<WageVo> wages = wageService.getWages();
            model.addAttribute("wages", wages);
            model.addAttribute("void", false);
            return "pages/wages";
        } catch (Exception e) {
            model.addAttribute("void", true);
            return "pages/wages";
        }
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
