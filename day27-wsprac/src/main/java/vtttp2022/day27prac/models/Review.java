package vtttp2022.day27prac.models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Review {
    private String user;
    private Integer rating;
    private String comment;
    private Integer gid;
    private LocalDateTime posted;
    private String name;
    private List<Edit> editList;

    public Review() {

    }
    public Review(String user, Integer rating, String comment, Integer gid) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.gid = gid;
        this.posted = LocalDateTime.now();
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public LocalDateTime getPosted() {
        return posted;
    }
    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Edit> getEditList() {
        return editList;
    }
    public void setEditList(List<Edit> editList) {
        this.editList = editList;
    }

    public Document toDocument() {
        Document d = new Document();
        d.put("user", user);
        d.put("rating", rating);
        d.put("comment", comment);
        d.put("gid", gid);
        d.put("posted", posted);
        d.put("name", name);

        return d;
    }    

    public static Review create(Document d) {
        Review r = new Review();
        r.user = d.getString("user");
        r.rating = d.getInteger("rating");
        r.comment = d.getString("comment") == null ? "" : d.getString("comment");
        r.gid = d.getInteger("gid");
        r.posted = d.getDate("posted").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        r.name = d.getString("name");

        List<Edit> eList = new LinkedList<>();

        try {
            List<Document> eDocList = d.getList("edited", Document.class);
            for (Document doc : eDocList) {
                Edit e = Edit.create(doc);
                eList.add(e);
            }
        } catch (Exception e) {
            // empty edit list
        }
        r.editList = eList;
        
        return r;
    }
    
    public Edit getLatestEdit() {
        return Collections.max(this.editList, Comparator.comparing(c -> c.getPosted()));
    }

    public JsonObject toJSON(boolean withLatestEdit) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("user", user);
        job.add("rating", rating);
        job.add("comment", comment);
        job.add("gid", gid);
        job.add("posted", posted.toString());
        job.add("name", name);

        if (withLatestEdit) {
            if (editList != null) {
                job.add("rating", editList.get(0).getRating());
                job.add("comment", editList.get(0).getComment());
                job.add("posted", editList.get(0).getPosted().toString());
                job.add("edited", true);
            } else {
                job.add("edited", false);
            }
            
        }

        return job.build();
    }

    public JsonObject toJSON() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("user", user);
        job.add("rating", rating);
        job.add("comment", comment);
        job.add("gid", gid);
        job.add("posted", posted.toString());
        job.add("name", name);

        if (editList != null) {
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (Edit e : editList) {
                jab.add(e.toJSON());
            }
            job.add("edited", jab.build());
        }

        return job.build();
    }
}
