package vttp2022.day21.day21.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String showIndex() {

        return "index";
    }

    // view that serves pagination
    // makes use of hidden input
}
