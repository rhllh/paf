package vttp2022.day27ws.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class BGRepository {
    
    @Autowired
    private MongoTemplate template;

    // check valid id
    // db.games.find({gameId: 123})
    public boolean checkValidGID(Integer gid) {
        
        return template.find(Query.query(Criteria.where("gameId").is(gid)), Document.class, "games").size() > 0;
    }

    // db.games.find({gameId: 123},{name:1})
    public String getGameTitleById(Integer gid) {
        
        return template.find(Query.query(Criteria.where("gameId").is(gid)), Document.class, "games").get(0).getString("name");
    }

    // db.reviews.find({reviewId: "123"})
    public boolean checkValidReviewID(String reviewId) {
        
        return template.find(Query.query(Criteria.where("reviewId").is(reviewId)), Document.class, "reviews").size() > 0;
    }

    // insert new review
    // db.reviews.insert({<document>})
    public Document insertReview(Document d) {
        Document inserted = template.insert(d, "reviews");

        return inserted;
    }

    // update existing review
    // db.reviews.insert({reviewId: "123"}, {$set: {edited: <document>}})
    public boolean updateReview(Document d, String reviewId) {
        Query q = Query.query(Criteria.where("reviewId").is(reviewId));
        Update updateOps = new Update().push("edited", d);

        UpdateResult updateResults = template.updateMulti(q, updateOps, Document.class, "reviews");

        return updateResults.getModifiedCount() > 0;
    }

    // retrieve review by id
    // db.reviews.find({reviewId: "123"})
    public Document getReview(String reviewId) {

        return template.find(Query.query(Criteria.where("reviewId").is(reviewId)), Document.class, "reviews").get(0);
    }
}
