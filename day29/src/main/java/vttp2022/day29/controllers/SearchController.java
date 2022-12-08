package vttp2022.day29.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.day29.services.MarvelService;
import vttp2022.day29.models.Character;
import vttp2022.day29.repositories.RedisRepo;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private MarvelService svc;

    @Autowired
    private RedisRepo repo;
    
    @GetMapping
    public String search(@RequestParam("hero") String hero, Model model) {

        List<Character> cList = null;

        Optional<List<Character>> opt = repo.get(hero);
        if (opt.isEmpty()) {
            cList = svc.searchCharByStartWith(hero);
            repo.setKey(hero, cList);
        } else {
            cList = opt.get();
        }

        model.addAttribute("cList", cList);

        return "results";
    }
}
