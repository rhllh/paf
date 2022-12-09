package vttp2022.day26ws.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.day26ws.models.Game;
import vttp2022.day26ws.services.GameService;

@RestController
@RequestMapping("/game")
public class SingleGameRESTController {
    
    @Autowired
    private GameService service;

    /*
     * GET /game/{game_id}
     * Accept: application/json
     * handle non-existing game ids
     * return json object of single game
     */
    @GetMapping(path="{gameId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGameById(@PathVariable("gameId") String id) {

        Integer gameId = 0;
        try {
            gameId = Integer.parseInt(id);
            System.out.println("is a numeric id");
        } catch (NumberFormatException e) {
            System.out.println("not a numeric id");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("%s not a valid id".formatted(id));
        }

        Game g = service.getGameById(gameId);
        if (g.getName() == "") {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("Game with id %s not found".formatted(id));
        }

        JsonObject j = g.toJSONById();
        System.out.println(j.toString());

        // final object
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("game_id", gameId);
        objectBuilder.add("name", g.getName());
        objectBuilder.add("year", g.getYear());
        objectBuilder.add("ranking", g.getRanking());
        objectBuilder.add("users_rated", g.getUserRated());
        objectBuilder.add("url", g.getUrl());
        objectBuilder.add("thumbnail", g.getThumbnail());
        objectBuilder.add("timestamp", new Date().toString());
        JsonObject jsonObj = objectBuilder.build();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jsonObj.toString());
    }
}
