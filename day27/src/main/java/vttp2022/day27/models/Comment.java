package vttp2022.day27.models;

import org.bson.Document;

public class Comment {
    private String user;
    private Integer rating;
    private String c_text;
    private Double score;

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
    public String getC_text() {
        return c_text;
    }
    public void setC_text(String c_text) {
        this.c_text = c_text;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

    public static Comment create(Document d) {
        Comment c = new Comment();
        c.setC_text(defaultValueString(d, "c_text"));
        c.setRating(d.getInteger("rating"));
        c.setUser(defaultValueString(d, "user"));
        c.setScore(defaultValueDouble(d, "score"));

        return c;
    }
    
    public static String defaultValueString(Document d, String k) {
        if (d.get(k) == null) {
            return "";
        } else {
            return d.getString(k);
        }
    }

    public static Double defaultValueDouble(Document d, String k) {
        if (d.get(k) == null) {
            return 0d;
        } else {
            if (d.get(k) instanceof Integer) {
                return ((Integer) d.get(k)).doubleValue();
            } else {
                return d.getDouble(k);
            }
        }
    }

}
