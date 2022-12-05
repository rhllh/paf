package vttp2022.day26.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TVShowRepo {

    public static final String C_TV_SHOWS = "tvshows";
    
    @Autowired
    private MongoTemplate template;

    // look for english language shows
    // db.tvshows.find({language:"English"})
    public List<Document> findTVShowByLang(String language) {

        // create criteria/predicate
        Criteria c = Criteria.where("language").is(language);

        // create query
        Query q = Query.query(c);

        List<Document> results = template.find(q, Document.class, C_TV_SHOWS);
        System.out.println("size of results : " + results.size());

        return results;
    }

    // db.tvshows.find({rating.average: $gte <f>})
    public List<Document> findTVShowsByRating(Float f) {
        
        Criteria c = Criteria.where("rating.average").gte(f);

        Query q = Query.query(c);

        List<Document> results = template.find(q, Document.class, C_TV_SHOWS);
        System.out.println("size of results : " + results.size());  // empty

        return results;
    }

    // db.tvshows.dictinct("genres")
    public List<String> findAllGenres() {

        List<String> genres = template.findDistinct(new Query(), "genres", C_TV_SHOWS, String.class);
    
        return genres;
    }

    // db.tvshows.find({genre:"<genre>"})
    public List<Document> findTVShowByGenre(String genre) {

        Criteria c = Criteria.where("genres").is(genre);

        Query q = Query.query(c).skip(0).limit(50);

        List<Document> results = template.find(q, Document.class, C_TV_SHOWS);

        // find(q, Document.class, C_TV_SHOWS).stream().map(v -> TVShow.create(d))

        return results;
    }
}
