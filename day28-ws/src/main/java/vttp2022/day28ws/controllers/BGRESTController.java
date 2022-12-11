package vttp2022.day28ws.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import vttp2022.day28ws.services.BGService;

@RestController
@RequestMapping("/api")
public class BGRESTController {

    @Autowired
    private BGService service;
    
    @GetMapping(path="/game/{gameId}/reviews", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGameReviews(@PathVariable("gameId") String gameIdString) {

        Integer gameId = 0;
        try {
            gameId = Integer.parseInt(gameIdString);
        } catch (NumberFormatException e) {
            System.out.println("gameId is not an integer");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("ID %s cannot be parsed as an integer".formatted(gameIdString));
        }

        if (!service.checkValidGameId(gameId)) {
            System.out.println("gameId not found in collection");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("ID %d is not found".formatted(gameId));
        }

        JsonObject j = service.getGameReviews(gameId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(j.toString());
    }

    @GetMapping(path="/games/{highOrLow}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGamesByRating(@PathVariable("highOrLow") String highOrLow) {
        highOrLow = highOrLow.toUpperCase();

        String[] values = {"HIGHEST", "LOWEST"};
        if (!Arrays.stream(values).anyMatch(highOrLow::equals)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wrong rating type %s".formatted(highOrLow));
        }
        
        JsonObject j = service.getGamesByRating(highOrLow);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(j.toString());
    }
}
