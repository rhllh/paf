package vttp2022.day26ws.services;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.day26ws.models.Game;
import vttp2022.day26ws.repositories.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository repo;

    // call repo
    public List<Game> getAllGames(Integer limit, Integer offset) {
        List<Document> results = repo.getAllGames(limit, offset);

        List<Game> games = new LinkedList<>();
        for (Document d : results) {
            Game g = Game.create(d);
            games.add(g);
        }
        
        return games;
    }

    public List<Game> getGamesByRank(Integer limit, Integer offset) {

        List<Document> results = repo.getGamesByRank(limit, offset);

        List<Game> games = new LinkedList<>();
        for (Document d : results) {
            Game g = Game.create(d);
            games.add(g);
        }
        
        return games;

    }

    public Game getGameById(Integer id) {
        Document result = repo.getGameById(id);
        if (result.size() == 0) {
            return new Game();
        }

        Game g = Game.create(result);

        return g;
    }
}
