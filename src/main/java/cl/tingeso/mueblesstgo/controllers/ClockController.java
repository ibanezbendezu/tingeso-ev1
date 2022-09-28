package cl.tingeso.mueblesstgo.controllers;

import cl.tingeso.mueblesstgo.services.ClockService;
import cl.tingeso.mueblesstgo.services.HRMService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ClockController {

    private final ClockService clockService;
    private final HRMService hrmService;

    public ClockController(ClockService clockService, HRMService hrmService) {
        this.clockService = clockService;
        this.hrmService = hrmService;
    }

    @GetMapping("/upload-clock")
    public String upload() {
        return "pages/upload-clock";
    }

    @PostMapping("/save-clock")
    public String save(@RequestParam("file") MultipartFile file, RedirectAttributes ms) {
        if (this.clockService.saveClock(file)) {
            try {
                this.clockService.loadClock();
                this.hrmService.generateWages();
                ms.addFlashAttribute("success", "Reloj cargado correctamente.");
                return "redirect:upload-clock";
            } catch (Exception e) {
                ms.addFlashAttribute("error", "Error al guardar el archivo.");
                return "redirect:upload-clock";
            }
        } else {
            ms.addFlashAttribute("error", "El archivo no posee el nombre correcto.");
            return "redirect:upload-clock";
        }
    }
}
