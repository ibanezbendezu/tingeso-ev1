package cl.tingeso.mueblesstgo.controllers;

import cl.tingeso.mueblesstgo.entities.JustificationEntity;
import cl.tingeso.mueblesstgo.services.JustificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("justification")
public class JustificationController {

    private final JustificationService justificationService;

    public JustificationController(JustificationService justificationService) {
        this.justificationService = justificationService;
    }

    @GetMapping
    public String justificationForm(Model model) {
        model.addAttribute("justification", new JustificationEntity());
        return "pages/provide-justification";
    }

    @PostMapping
    public String justificationSubmit(@ModelAttribute JustificationEntity justification, @RequestParam("rut") String rut, Model model) {
        try {
            model.addAttribute("justification", justificationService.saveJustification(justification, rut));
            return "pages/justification-result";
        } catch (Exception e) {
            model.addAttribute("justification", justification);
            model.addAttribute("error", "El rut ingresado no existe.");
            return "pages/provide-justification";
        }
    }

    //@PostMapping
    /*
    public String justificationSubmit2(@ModelAttribute JustificationEntity justification, Model model) {
        String errMessage = justificationService.isValid(justification);
        if (errMessage != null) {
            model.addAttribute("justification", justification);
            model.addAttribute("error", errMessage);
            return "pages/provide-justification";
        }
        model.addAttribute("justification", justificationService.saveJustification(justification));
        return "pages/justification-result";
    }*/
}
