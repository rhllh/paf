package vttp2022.day28ws.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BGRepo {
    
    @Autowired
    private MongoTemplate template;
    
    // db.games.find({gameId: 123})
    public boolean checkValidGameId(Integer gameId) {

        return template.find(Query.query(Criteria.where("gid").is(gameId)), Document.class, "games").size() > 0;
    }

    public Document getGameReviews(Integer gameId) {

        System.out.println("gameId > " + gameId);

        MatchOperation match = Aggregation.match(Criteria.where("gid").is(gameId));
        LookupOperation lookup = Aggregation.lookup("reviews", "gid", "ID", "reviews");
        
        Aggregation pipeline = Aggregation.newAggregation(match, lookup);
        AggregationResults<Document> results = template.aggregate(pipeline, "games", Document.class);

        List<Document> resultList = new LinkedList<>();
        for (Document d : results) { 
            resultList.add(d);
        }

        return resultList.get(0);
    }

    /*
     * db.games.aggregate([
            {
                $lookup: {
                    from: 'reviews',
                    foreignField: 'ID',
                    localField: 'gid',
                    as: 'review'
                }
            },
            {
                $unwind: "$review"
            },
            {
                $sort: { "review.rating" : -1}
            }
        ])
     */
    public List<Document> getGamesByRating(String highOrLow) {

        Direction dir;
        if (highOrLow.equals("HIGHEST")) {
            dir = Direction.DESC;
        } else {
            dir = Direction.ASC;
        }

        LookupOperation lookup = Aggregation.lookup("reviews", "gid", "ID", "reviews");
        UnwindOperation unwind = Aggregation.unwind("reviews");
        SortOperation sort = Aggregation.sort(dir, "reviews.rating");

        Aggregation pipeline = Aggregation.newAggregation(lookup, unwind, sort);
        AggregationResults<Document> results = template.aggregate(pipeline, "games", Document.class);

        List<Document> resultList = new LinkedList<>();
        String name = "";
        for (Document d : results) {
            Document reviews = (Document) d.get("reviews");
            String nameFromDoc = reviews.getString("name");
            if (!nameFromDoc.equals(name)) {
                System.out.println(d.toString());
                resultList.add(d);
            }
            name = nameFromDoc;
        }

        return resultList;
    }
}
