package vttp2022.day27ws.controllers;

import java.util.Date;
import java.util.List;

import org.bson.Document;
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
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.day27ws.models.Review;
import vttp2022.day27ws.services.BGService;

@RestController
@RequestMapping("/api")
public class BGRESTController {

    @Autowired
    private BGService service;

    @PostMapping(path = "/review", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postReview(@RequestBody MultiValueMap<String, String> form) {

        String name = form.getFirst("name") == null ? "" : form.getFirst("name");
        String comment = form.getFirst("comment") == null ? "" : form.getFirst("comment");
        Integer rating = 0;
        Integer gid = 0;
        
        // rating and id should be integer
        try {
            rating = Integer.parseInt(form.getFirst("rating"));
            gid = Integer.parseInt(form.getFirst("gid"));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("value for rating or id is not an integer");
        }

        // check for invalid game id
        if (!service.checkValidGID(gid)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not a valid id in collection");
        }

        // insert into collection
        Document inserted = service.insertReviewToCollection(name, comment, rating, gid);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Review.create(inserted).toJSON().toString());
    }

    @PutMapping(path = "/review/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(@PathVariable("reviewId") String reviewId, 
                             @RequestBody Review review) {
        
        Integer rating = review.getRating();
        String comment = review.getComment();

        // check for valid review ID
        if (!service.checkValidReviewID(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review ID %s is not found".formatted(reviewId));
        }

        // update review
        boolean updated = service.updateReviewInCollection(comment, rating, reviewId);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Review ID %s did not update successfully".formatted(reviewId));
        }

        return ResponseEntity.status(HttpStatus.OK).body("Review ID %s successfully updated".formatted(reviewId));
    }

    @GetMapping(path = "/review/{reviewId}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReview(@PathVariable("reviewId") String reviewId) {

        // check for valid review ID
        if (!service.checkValidReviewID(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review ID %s is not found".formatted(reviewId));
        }

        Document result = service.getReviewFromCollection(reviewId);

        JsonObject j = Json.createObjectBuilder()
                .add("user", result.getString("user"))
                .add("rating", result.getInteger("rating"))
                .add("comment", result.getString("comment"))
                .add("gameId", result.getInteger("gameId"))
                .add("posted", result.getDate("posted").toString())
                .add("name", result.getString("name"))
                .add("edited", String.valueOf(result.get("edited") != null))
                .add("timestamp", new Date().toString())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(j.toString());
    }

    @GetMapping(path = "/review/{reviewId}/history", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewHistory(@PathVariable("reviewId") String reviewId) {

        // check for valid review ID
        if (!service.checkValidReviewID(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review ID %s is not found".formatted(reviewId));
        }

        Document result = service.getReviewFromCollection(reviewId);

        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("user", result.getString("user"));
        job.add("rating", result.getInteger("rating"));
        job.add("comment", result.getString("comment"));
        job.add("gameId", result.getInteger("gameId"));
        job.add("posted", result.getDate("posted").toString());
        job.add("name", result.getString("name"));
        if (result.get("edited") != null) {
            List<Document> edited = (List<Document>) result.get("edited");
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (Document d : edited) {
                jab.add(Json.createObjectBuilder()
                        .add("comment", d.getString("comment"))
                        .add("rating", d.getInteger("rating"))
                        .add("posted", d.getDate("posted").toString()));
            }
            job.add("edited", jab.build());
        }
        job.add("timestamp", new Date().toString());
        JsonObject j = job.build();

        return ResponseEntity.status(HttpStatus.OK).body(j.toString());
    }
}
