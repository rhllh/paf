package vttp2022.day28.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2022.day28.models.Game;

@Repository
public class GameRepo {
    
    @Autowired
    private MongoTemplate template;

    /*
    db.games.aggregate([
        {
            $match: {
                name: { $regex: 'monopoly', $options: 'i' }
            }
        },
        {
            $lookup: {
                from: 'comment',
                foreignField: 'gid',
                localField: 'gid',
                as: 'comments'
            }
        },
        {
            $unwind: '$comments'
        },
        {
            $sort: { "comments.rating": -1 }
        },
            {
            $project: {
                _id: 1, name: 1, 
                user: "$comments.user", 
                comment: "$comments.c_text", 
                rating: "$comments.rating", 
                image: 1
            }
        },
        {
            $limit: 10
        }
    ])
    */
    public List<Game> search(String name) {
        // $match the name
        MatchOperation match = Aggregation.match(Criteria.where("name").regex(name, "i"));

        // $lookup
        LookupOperation lookup = Aggregation.lookup("comment", "gid", "gid", "comments");

        // $project _id, name
        ProjectionOperation projection = Aggregation.project("_id", "name", "image", "comments");

        // $unwind comments
        UnwindOperation unwind = Aggregation.unwind("comments");

        // $sort
        SortOperation sort = Aggregation.sort(Direction.DESC, "comments.rating");

        // $limit
        LimitOperation limit = Aggregation.limit(10);
        
        // $project { _id: 1, name: 1, user: "$comments.user", comment: "$comments.c_text", rating: "$comments.rating", image: 1 }
        ProjectionOperation projection2 = Aggregation.project("_id", "name", "image")
                            .and("comments.user").as("user")
                            .and("comments.c_text").as("comment")
                            .and("comments.rating").as("rating");

        Aggregation pipeline = Aggregation.newAggregation(match, lookup, projection, unwind, sort, limit, projection2);

        AggregationResults<Document> results = template.aggregate(pipeline, "games", Document.class);

        if (!results.iterator().hasNext()) {
            return new LinkedList<>();
        }
        
        List<Game> gameList = new LinkedList<>();
        for (Document d : results) {
            Game g = Game.create(d);
            gameList.add(g);
        }

        return gameList;
    }

    // db.games.find({name: "<name>"})
    public List<Game> searchGame(String name) {
        Criteria c = Criteria.where("name").is(name);

        Query q = Query.query(c);

        List<Game> results = template.find(q, Document.class, "games")
                                    .stream()
                                    .map(d -> Game.create(d))
                                    .toList();
        System.out.println("size of results : " + results.size()); 

        return results;
    }
}
