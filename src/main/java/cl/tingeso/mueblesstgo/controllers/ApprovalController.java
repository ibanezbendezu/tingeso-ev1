package cl.tingeso.mueblesstgo.controllers;

import cl.tingeso.mueblesstgo.services.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping
public class ApprovalController {

    @Autowired
    ApprovalService approvalService;

    @GetMapping("/provide-approval")
    public String upload() {
        return "pages/provide-approval";
    }

    @PostMapping("/save-approval")
    public String save(@RequestParam Map<String,String> allParams){
        approvalService.saveApproval(allParams);
        return "redirect:/provide-approval";
    }
}
