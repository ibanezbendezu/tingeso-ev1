package cl.tingeso.mueblesstgo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "")
    public String getHome() {
        return "pages/home";
    }

    @GetMapping("/wages")
    public String getWageSearch() {
        return "pages/wages";
    }
}
