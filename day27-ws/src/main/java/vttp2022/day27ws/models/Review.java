package vttp2022.day27ws.models;

import java.util.Date;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Review {
    private String reviewId;
    private String user;
    private String comment;
    private Integer rating;
    private Integer gameId;
    private Date posted;
    private String name;
    
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
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
    public Integer getGameId() {
        return gameId;
    }
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
    public Date getPosted() {
        return posted;
    }
    public void setPosted(Date posted) {
        this.posted = posted;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getReviewId() {
        return reviewId;
    }
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public static Review create(Document d) {
        Review r = new Review();
        r.reviewId = d.getString("reviewId");
        r.user = d.getString("user");
        r.comment = d.getString("comment");
        r.rating = d.getInteger("rating");
        r.gameId = d.getInteger("gameId");
        r.posted = d.getDate("posted");
        r.name = d.getString("name");

        return r;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("reviewId", getReviewId())
                .add("user", getUser())
                .add("comment", getComment())
                .add("rating", getRating())
                .add("gameId", getGameId())
                .add("posted", getPosted().toString())
                .add("name", getName())
                .build();
    }

    
}
