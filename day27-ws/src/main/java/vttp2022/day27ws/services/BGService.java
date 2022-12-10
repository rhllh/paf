package vttp2022.day27ws.services;

import java.util.Date;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.day27ws.repositories.BGRepository;

@Service
public class BGService {
    
    @Autowired
    private BGRepository repo;

    public boolean checkValidGID(Integer gid) {
        return repo.checkValidGID(gid);
    }

    public boolean checkValidReviewID(String reviewId) {
        return repo.checkValidReviewID(reviewId);
    }

    // take arguments -> document
    public Document insertReviewToCollection(String name, String comment,
                                            Integer rating, Integer gid) {

        // get name of game by id
        String gameTitle = repo.getGameTitleById(gid);
        
        Document d = new Document();
        d.put("reviewId", UUID.randomUUID().toString().substring(0,8));
        d.put("user", name);
        d.put("rating", rating);
        d.put("comment", comment);
        d.put("gameId", gid);
        d.put("posted", new Date());
        d.put("name", gameTitle);

        Document inserted = repo.insertReview(d);

        return inserted;
    }

    public boolean updateReviewInCollection(String comment, Integer rating, String reviewId) {
        
        Document d = new Document();
        d.put("comment", comment);
        d.put("rating", rating);
        d.put("posted", new Date());

        return repo.updateReview(d, reviewId);
    }

    public Document getReviewFromCollection(String reviewId) {

        return repo.getReview(reviewId);
    }
}
