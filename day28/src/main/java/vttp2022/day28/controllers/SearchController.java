package vttp2022.day28.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.day28.models.Game;
import vttp2022.day28.repositories.GameRepo;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private GameRepo repo;
    
    @GetMapping
    public String getGame(@RequestParam("game") String name, Model model) {

        System.out.println("name > " + name);

        List<Game> results = repo.search(name);
        System.out.println("resultSize > " + results.size());

        model.addAttribute("game", results);
        model.addAttribute("resultSize", results.size());

        return "results";
    }
    
}
