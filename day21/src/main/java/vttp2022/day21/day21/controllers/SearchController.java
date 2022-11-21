package vttp2022.day21.day21.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.day21.day21.models.Book;
import vttp2022.day21.day21.repositories.BookRepository;

@Controller
@RequestMapping("/search")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private BookRepository bookRepo;
    
    @GetMapping
    public String showResults(@RequestParam("title") String title, 
                              @RequestParam("numResult") String numResult,
                              Model model) {
        logger.info("title >> " + title);
        logger.info("numResult >> " + numResult);

        List<Book> results = bookRepo.getBooksByTitleAndLimit(title, Integer.parseInt(numResult));

        model.addAttribute("bookResults", results);
        model.addAttribute("title", title);

        return "search-result";
    }
}
