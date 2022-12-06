package vttp2022.day27.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import vttp2022.day27.models.Comment;

@Repository
public class CommentRepo {
    
    @Autowired
    private MongoTemplate template;

    // db.comment.find({ $text: { $search: "<word1 word2>" }, { score: { $meta: "textScore" } })
    public List<Comment> textSearch(String terms, Float minScore) {

        TextCriteria tc = TextCriteria.forDefaultLanguage()
                            .matchingAny(terms);

        TextQuery tq = (TextQuery) TextQuery.queryText(tc).includeScore("score").limit(15);

        List<Comment> results = template.find(tq, Document.class, "comment")
                                    .stream()
                                    .map(d -> Comment.create(d))
                                    .filter(c -> c.getScore() >= minScore)
                                    .toList();

        return results;
    }

    public List<Comment> textSearchIncludeExclude(String terms, Float minScore) {
        List<String> include = new LinkedList<>();
        List<String> exclude = new LinkedList<>();

        String[] termsArr = terms.split(" ");
        for (String t : termsArr) {
            if (t.startsWith("-")) {
                exclude.add(t);
            } else {
                include.add(t);
            }
        }

        TextCriteria tc = TextCriteria.forDefaultLanguage()
                            .matchingAny(include.toArray(new String[include.size()]))
                            .notMatchingAny(exclude.toArray(new String[exclude.size()]));

        TextQuery tq = (TextQuery) TextQuery.queryText(tc).includeScore("score").limit(15);

        List<Comment> results = template.find(tq, Document.class, "comment")
                                    .stream()
                                    .map(d -> Comment.create(d))
                                    .filter(c -> c.getScore() >= minScore)
                                    .toList();

        return results;
    }
}
