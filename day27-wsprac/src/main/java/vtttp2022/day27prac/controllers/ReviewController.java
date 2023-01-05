package vtttp2022.day27prac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.JsonObject;
import vtttp2022.day27prac.services.ReviewService;

@Controller
@RequestMapping("/web/review")
public class ReviewController {
    
    @Autowired
    private ReviewService svc;

    @GetMapping
    public String getReviewHistoryById(@RequestParam("reviewId") String reviewId, Model model) {
        
        // check valid review id
        boolean valid = svc.checkRIDExists(reviewId);
        if (!valid) {
            System.out.println("invalid ID");
            return "not-found";
        }

        // get review history by id
        JsonObject resp = svc.retrieveReviewHistoryById(reviewId);

        model.addAttribute("review", resp);

        return "reviews";
    }
}
