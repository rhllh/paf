package vttp2022.day26.controllers;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp2022.day26.models.TVShow;
import vttp2022.day26.repositories.TVShowRepo;

@RestController
@RequestMapping(path="/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class TVShowRESTController {

    @Autowired
    private TVShowRepo tvShowRepo;

    // /genres - return array of string
    @GetMapping("/genres")
    public ResponseEntity<String> getGenres() {

        List<String> genreList = tvShowRepo.findAllGenres();
        
        JsonArrayBuilder jb = Json.createArrayBuilder();
        for (String g : genreList) {
            jb.add(g);
        }
        JsonArray arr = jb.build();

        return ResponseEntity.status(HttpStatus.OK).body(arr.toString());
    }

    // /tvshow/{genre} - return json of name, summary, image/original as image, rating(average)
    @GetMapping("/tvshow/{genre}")
    public ResponseEntity<String> getTVShowByGenre(@PathVariable("genre") String genre) {
        System.out.println("genre : " + genre);

        List<Document> results = tvShowRepo.findTVShowByGenre(genre);
        List<TVShow> tvShowList = new LinkedList<>();
        for (Document d : results) {
            TVShow show = TVShow.create(d);
            tvShowList.add(show);
        }
        
        JsonArrayBuilder jb = Json.createArrayBuilder();
        for (TVShow t : tvShowList) {
            jb.add(t.toJSON());
        }
        JsonArray arr = jb.build();

        return ResponseEntity.status(HttpStatus.OK).body(arr.toString());
    }
}
