package vttp2022.day26ws.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository {
    
    @Autowired
    private MongoTemplate template;

    // call mongodb
    public List<Document> getAllGames(Integer limit, Integer offset) {

        List<Document> games = new LinkedList<>();

        // db.games.find({})
        List<Document> results = template.find(new Query(), Document.class, "games");

        // .limit(25).skip(10)
        for (int i = offset; i < offset+limit; i++) {
            games.add(results.get(i));
        }

        return games;
    }

    public List<Document> getGamesByRank(Integer limit, Integer offset) {
        List<Document> games = new LinkedList<>();

        // db.games.find({})
        List<Document> results = template.find(new Query().with(Sort.by(Sort.Direction.ASC, "ranking")), Document.class, "games");

        // .limit(25).skip(10)
        for (int i = offset; i < offset+limit; i++) {
            games.add(results.get(i));
        }

        return games;
    }

    public Document getGameById(Integer id) {
        List<Document> result = template.find(Query.query(Criteria.where("gid").is(id)), Document.class, "games");

        System.out.println("result size > " + result.size());

        return result.get(0);
    }
}
