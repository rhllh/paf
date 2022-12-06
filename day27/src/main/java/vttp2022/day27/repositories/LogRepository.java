package vttp2022.day27.repositories;

import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogRepository {
    
    @Autowired
    private MongoTemplate template;

    public void log(String terms, Float score) {
        Document s = new Document();
        s.put("terms", terms);
        s.put("score", score);
        s.put("date", new Date());

        template.insert(s, "logs");
    }
}
