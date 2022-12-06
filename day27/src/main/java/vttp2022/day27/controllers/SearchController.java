package vttp2022.day27.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.day27.models.Comment;
import vttp2022.day27.repositories.CommentRepo;
import vttp2022.day27.repositories.LogRepository;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private LogRepository logRepo;
    
    @GetMapping
    public String getForm(@RequestParam("searchTerms") String terms,
                        @RequestParam("score") String minScore,
                        Model model) {

        Float score = Float.parseFloat(minScore);

        //List<Comment> results = commentRepo.textSearch(terms, score);
        List<Comment> results = commentRepo.textSearchIncludeExclude(terms, score);
        logRepo.log(terms, score);

        model.addAttribute("terms", terms);
        model.addAttribute("minScore", minScore);
        model.addAttribute("comments", results);

        return "comment";
    }
}
