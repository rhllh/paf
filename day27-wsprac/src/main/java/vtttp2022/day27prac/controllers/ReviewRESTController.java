package vtttp2022.day27prac.controllers;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vtttp2022.day27prac.models.Edit;
import vtttp2022.day27prac.models.Review;
import vtttp2022.day27prac.services.ReviewService;

@RestController
@RequestMapping(path="/review", produces=MediaType.APPLICATION_JSON_VALUE)
public class ReviewRESTController {

    @Autowired
    private ReviewService reviewService;
    
    @PostMapping(consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> postReview(@RequestBody MultiValueMap<String, String> form) {

        String name = form.getFirst("name");
        int rating = Integer.parseInt(form.getFirst("rating"));
        String comment = form.getFirst("comment") == null ? "" : form.getFirst("comment");
        int gameId = Integer.parseInt(form.getFirst("gameId"));

        // check valid game id
        boolean valid = reviewService.checkGIDExists(gameId);
        if (!valid) {
            System.out.println("invalid ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Game ID > %d".formatted(gameId));
        }

        // insert review into 'reviews' collection
        Review r = new Review(name, rating, comment, gameId);
        boolean inserted = reviewService.insertReview(r);
        if (!inserted) {
            System.out.println("not created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Game ID > %d".formatted(gameId));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Review created for Game ID > %d".formatted(gameId));

    }

    @PutMapping(path="/{reviewId}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(@PathVariable String reviewId, @RequestBody String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObj = reader.readObject();

        // check valid game id
        boolean valid = reviewService.checkRIDExists(reviewId);
        if (!valid) {
            System.out.println("invalid ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid review ID > %s".formatted(reviewId));
        }

        // update review
        Edit e = new Edit(jsonObj.getString("comment"), jsonObj.getInt("rating"));
        boolean updated = reviewService.updateReview(reviewId, e);

        if (!updated) {
            System.out.println("not updated");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid review ID > %d".formatted(reviewId));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Updated review for review ID > %s".formatted(reviewId));
    }

    @GetMapping(path="/{reviewId}")
    public ResponseEntity<String> retrieveLatestReview(@PathVariable String reviewId) {

        // check valid review id
        boolean valid = reviewService.checkRIDExists(reviewId);
        if (!valid) {
            System.out.println("invalid ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid review ID > %s".formatted(reviewId));
        }

        // get review by id and get latest edited (if any)
        JsonObject resp = reviewService.retrieveLatestReviewById(reviewId);

        return ResponseEntity.status(HttpStatus.OK).body(resp.toString());
    }

    @GetMapping(path="/{reviewId}/history")
    public ResponseEntity<String> retrieveReviewHistory(@PathVariable String reviewId) {

        // check valid review id
        boolean valid = reviewService.checkRIDExists(reviewId);
        if (!valid) {
            System.out.println("invalid ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid review ID > %s".formatted(reviewId));
        }

        // get review history by id
        JsonObject resp = reviewService.retrieveReviewHistoryById(reviewId);

        return ResponseEntity.status(HttpStatus.OK).body(resp.toString());
    }
}
