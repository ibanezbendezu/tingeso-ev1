package cl.tingeso.mueblesstgo.controllers;

import cl.tingeso.mueblesstgo.services.JustificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping
public class JustificationController {

    @Autowired
    JustificationService justificationService;

    @GetMapping("/provide-justification")
    public String upload() {
        return "pages/provide-justification";
    }

    @PostMapping("/save-justification")
    public String save(@RequestParam Map<String,String> allParams){
        justificationService.saveJustification(allParams);
        return "redirect:/provide-justification";
    }
}
