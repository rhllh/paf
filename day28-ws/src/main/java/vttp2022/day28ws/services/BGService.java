package vttp2022.day28ws.services;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.day28ws.repositories.BGRepo;

@Service
public class BGService {
    
    @Autowired
    private BGRepo repo;

    public boolean checkValidGameId(Integer gameId) {

        return repo.checkValidGameId(gameId);
    }

    public JsonObject getGameReviews(Integer gameId) {

        Document result = repo.getGameReviews(gameId);

        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("game_id", result.getInteger("gid"));
        job.add("name", result.getString("name"));
        job.add("year", result.getInteger("year"));
        job.add("rank", result.getInteger("ranking"));
        job.add("url", result.getString("url"));
        job.add("thumbnail", result.getString("image"));
        
        List<Document> reviewList = (List<Document>) result.get("reviews");
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Document d : reviewList) {
            // System.out.println(d.toString());
            jab.add(d.getString("reviewId") == null ? "" : ("/review/" + d.getString("reviewId")));
        }
        job.add("reviews", jab.build());

        job.add("timestamp", new Date().toString());
        JsonObject j = job.build();

        return j;
    }

    public JsonObject getGamesByRating(String highOrLow) {

        List<Document> results = repo.getGamesByRating(highOrLow);

        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Document d : results) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            Document r = (Document) d.get("reviews");
            job.add("_id", r.getInteger("gameId"));
            job.add("name", r.getString("name") == null ? "" : r.getString("name"));
            job.add("rating", r.getInteger("rating") == null ? 0 : r.getInteger("rating"));
            job.add("user", r.getString("user") == null ? "" : r.getString("user"));
            job.add("comment", r.getString("comment") == null ? "" : r.getString("comment"));
            job.add("review_id", r.getString("reviewId") == null ? "" : r.getString("reviewId"));
            jab.add(job.build());
            
        }
        
        JsonObjectBuilder job2 = Json.createObjectBuilder();
        job2.add("rating", highOrLow);
        job2.add("games", jab.build());
        job2.add("timestamp", new Date().toString());
        JsonObject j = job2.build();

        return j;
    }
}
