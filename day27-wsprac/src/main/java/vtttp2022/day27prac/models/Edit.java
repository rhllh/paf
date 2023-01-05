package vtttp2022.day27prac.models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Edit {
    private String comment;
    private Integer rating;
    private LocalDateTime posted;

    public Edit() {}
    public Edit(String comment, Integer rating) {
        this.comment = comment;
        this.rating = rating;
        this.posted = LocalDateTime.now();
    }
    
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public LocalDateTime getPosted() {
        return posted;
    }
    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    public Document toDocument() {
        Document d = new Document();
        d.put("comment", comment);
        d.put("rating", rating);
        d.put("posted", posted);

        return d;
    }
    
    public static Edit create(Document d) {
        Edit e = new Edit();
        e.comment = d.getString("comment") == null ? "" : d.getString("comment");
        e.rating = d.getInteger("rating");
        e.posted = d.getDate("posted").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return e;
    }

    public static Edit getLatestEdit(List<Edit> edits) {
        return Collections.max(edits, Comparator.comparing(c -> c.getPosted()));
    }

    public JsonObject toJSON() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("comment", comment);
        job.add("rating", rating);
        job.add("posted", posted.toString());
        
        return job.build();
    }
}
