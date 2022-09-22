package cl.tingeso.mueblesstgo.controllers;

import cl.tingeso.mueblesstgo.services.ClockService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ClockController {

    private final ClockService clockService;

    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }

    @GetMapping("/upload-clock")
    public String upload() {
        return "pages/upload-clock";
    }

    @PostMapping("/save-clock")
    public String save(@RequestParam("file") MultipartFile file, RedirectAttributes ms) {
        try {
            this.clockService.saveClock(file);
            ms.addFlashAttribute("success", "Archivo guardado correctamente!!");
            return "pages/upload-clock";
        } catch (Exception e) {
            ms.addFlashAttribute("error", "Error al guardar el archivo.");
            return "pages/upload-clock";
        }
    }
}
