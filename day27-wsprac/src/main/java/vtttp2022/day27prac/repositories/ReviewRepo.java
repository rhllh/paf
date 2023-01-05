package vtttp2022.day27prac.repositories;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class ReviewRepo {

    @Autowired
    private MongoTemplate template;

    /*
     * db.reviews.find({_id: ObjectId("xxxx")})
     */
    public boolean checkRIDExists(String reviewId) {
        List<Document> result = template.find(Query.query(Criteria.where("_id").is(new ObjectId(reviewId))), Document.class, "reviews");
        System.out.println("size of result > " + result.size());
        
        if (result.size() > 0) return true;
        else return false;
    }

    /*
     * db.reviews.find({_id: ObjectId("xxxx")})
     */
    public Document retrieveReviewById(String reviewId) {
        List<Document> result = template.find(Query.query(Criteria.where("_id").is(new ObjectId(reviewId))), Document.class, "reviews");
        
        return result.get(0);
    }
    
    /*
     * db.reviews.insert({
            name: "henry",
            rating: 9,
            comment: "",
            gid: 10,
            ..
        })
     */
    public boolean insertReview(Document d) {
        Document inserted = template.insert(d, "reviews");
        return inserted != null;
    }

    /*
     * db.reviews.updateOne(
            { _id: ObjectId("xxxx") },
            {
                $push: { 
                        edited: { comment: "xxx", rating: 123, posted: xxx }
                    }
            }
        )
     */
    public boolean updateReview(String reviewId, Document d) {
        Query q = Query.query(Criteria.where("_id").is(new ObjectId(reviewId)));

        Update updateOps = new Update().push("edited").each(d);

        UpdateResult result = template.updateFirst(q, updateOps, Document.class, "reviews");

        return result.getModifiedCount() > 0;
    }
}
