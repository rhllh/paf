package vttp2022.day21.day21.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.day21.day21.models.Book;
import vttp2022.day21.day21.repositories.BookRepository;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private BookRepository bookRepo;
    
    @GetMapping
    public String showResults(@RequestParam("title") String title,
                              Model model) {

        List<Book> results = bookRepo.getBooksByTitleAndOffset(title, 0);

        model.addAttribute("bookResults", results);
        model.addAttribute("title", title);
        model.addAttribute("offset", 0);

        return "search-result";
    }

    @PostMapping("/next")
    public String showNextPage(@RequestParam("offset") String offsetString, 
                                @RequestParam("title") String title, Model model) {
        
        Integer offset = Integer.parseInt(offsetString) + 5;

        List<Book> results = bookRepo.getBooksByTitleAndOffset(title, offset);

        model.addAttribute("bookResults", results);
        model.addAttribute("title", title);
        model.addAttribute("offset", offset);

        return "search-result";
    }

    @PostMapping("/previous")
    public String showPrevPage(@RequestParam("offset") String offsetString, 
                                @RequestParam("title") String title, Model model) {
        
        Integer offset = Integer.parseInt(offsetString) - 5;

        List<Book> results = bookRepo.getBooksByTitleAndOffset(title, offset);

        model.addAttribute("bookResults", results);
        model.addAttribute("title", title);
        model.addAttribute("offset", offset);

        return "search-result";
    }
}
