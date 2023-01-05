package vtttp2022.day27prac.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepo {
    
    @Autowired
    private MongoTemplate template;

    /*
     * db.games.find({gid: 11})
     */
    public boolean checkGIDExists(Integer gid) {
        List<Document> result = template.find(Query.query(Criteria.where("gid").is(gid)), Document.class, "games");

        if (result.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getGameNameByGID(Integer gid) {
        List<Document> result = template.find(Query.query(Criteria.where("gid").is(gid)), Document.class, "games");

        return result.get(0).getString("name");
    }
}
