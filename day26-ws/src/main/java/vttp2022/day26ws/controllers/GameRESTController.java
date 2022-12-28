package vttp2022.day26ws.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.day26ws.models.Game;
import vttp2022.day26ws.services.GameService;

@RestController
@RequestMapping("/games")
public class GameRESTController {

    @Autowired
    private GameService service;
    
    /*
     * GET /games(?limit=1&offset=1)
     * Accept: application/json
     * returns JSON object
     * { games: [],
     *   offset: 0,
     *   limit: 0,
     *   total: 0,
     *   timestamp: ""
     * }
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllGames(@RequestParam(value="limit", required=false, defaultValue="25") Integer limit,
                                              @RequestParam(value="offset", required=false, defaultValue="0") Integer offset) {
        
        System.out.println("limit > " + limit);
        System.out.println("offset > " + offset);

        // call service
        List<Game> games = service.getAllGames(limit, offset);

        // games array
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Game g : games) {
            arrayBuilder.add(g.toJSON());
        }
        JsonArray arr = arrayBuilder.build();

        // final object
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("games", arr);
        objectBuilder.add("offset", offset);
        objectBuilder.add("limit", limit);
        objectBuilder.add("total", games.size());
        objectBuilder.add("timestamp", new Date().toString());
        JsonObject jsonObj = objectBuilder.build();

        return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());
    }

    /*
     * GET /games/rank
     * Accept: application/json
     * games in ascending ranking order
     * same return format as getAllGames()
     */
    @GetMapping(path="/rank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGamesByRank(@RequestParam(value="limit", required=false, defaultValue="25") Integer limit,
                               @RequestParam(value="offset", required=false, defaultValue="0") Integer offset) {
        // call service
        List<Game> games = service.getGamesByRank(limit, offset);

        // games array
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Game g : games) {
            arrayBuilder.add(g.toJSONRank());
        }
        JsonArray arr = arrayBuilder.build();

        // final object
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("games", arr);
        objectBuilder.add("offset", offset);
        objectBuilder.add("limit", limit);
        objectBuilder.add("total", games.size());
        objectBuilder.add("timestamp", new Date().toString());
        JsonObject jsonObj = objectBuilder.build();

        return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());
    }

}
