package vttp2022.day28.models;

import org.bson.Document;

public class Game {
    private String name;
    private String user;
    private String image;
    private String comment;
    private Double rating;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    

    public static Game create(Document d) {
        Game g = new Game();
        g.setName(defaultValueString(d,"name"));
        g.setUser(defaultValueString(d, "user"));
        g.setImage(defaultValueString(d, "image"));
        g.setComment(defaultValueString(d, "comment"));
        g.setRating(defaultValueDouble(d, "rating"));

        return g;
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

    public static Integer defaultValueInteger(Document d, String k) {
        if (d.get(k) == null) {
            return 0;
        } else {
            return d.getInteger(k);
        }
    }
 
}
