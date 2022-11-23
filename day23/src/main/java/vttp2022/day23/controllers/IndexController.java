package vttp2022.day23.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.day23.models.Style;
import vttp2022.day23.repositories.StyleRepository;

@Controller
@RequestMapping(path={"/","/index.html"})
public class IndexController {

    @Autowired
    private StyleRepository styleRepo;
    
    @GetMapping
    public String showIndex(Model model) {

        List<Style> styleList = styleRepo.getStyle();

        model.addAttribute("styleList", styleList);

        return "index";
    }
}
