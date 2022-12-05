package vttp2022.day26.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class TVShow {
    private String name;
    private String summary;
    private String image;
    private Double rating;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public static TVShow create(Document d) {
        TVShow show = new TVShow();
        show.setName(defaultValueString(d, "name"));
        show.setSummary(defaultValueString(d, "summary"));
        Document dim = d.get("image", Document.class);
        show.setImage(defaultValueString(dim, "original"));
        Document dr = d.get("rating", Document.class);
        show.setRating(defaultValueDouble(dr, "average"));

        return show;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("name",getName())
            .add("summary",getSummary())
            .add("image",getImage())
            .add("rating",getRating())
            .build();
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
