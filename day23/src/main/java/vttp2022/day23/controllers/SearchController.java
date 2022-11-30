package vttp2022.day23.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.day23.models.Brewery;
import vttp2022.day23.repositories.BreweryRepository;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private BreweryRepository breweryRepo;

    @GetMapping
    public String getBrewery(@RequestParam("styleId") Integer styleId,
                             Model model) {

        // look-aside caching
        List<Brewery> brList = breweryRepo.getBreweriesRedis(styleId);

        model.addAttribute("breweries", brList);

        return "search";
    }
}
