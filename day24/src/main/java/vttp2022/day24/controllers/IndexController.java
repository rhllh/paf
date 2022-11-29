package vttp2022.day24.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/index.html"})
public class IndexController {
    
    @GetMapping
    public String showForm() {
        return "index";
    }
}
