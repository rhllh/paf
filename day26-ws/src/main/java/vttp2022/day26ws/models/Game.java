package vttp2022.day26ws.models;

import java.util.Date;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Game {
    private String name;
    private Integer id;
    private Integer ranking;
    private Integer year;
    private Integer userRated;
    private String url;
    private String thumbnail;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getUserRated() {
        return userRated;
    }
    public void setUserRated(Integer userRated) {
        this.userRated = userRated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public static Game create(Document d) {
        Game g = new Game();
        g.name = defaultValueString(d, "name");
        g.id = defaultValueInteger(d, "gid");
        g.ranking = defaultValueInteger(d, "ranking");
        g.year = defaultValueInteger(d, "year");
        g.userRated = defaultValueInteger(d, "users_rated");
        g.thumbnail = defaultValueString(d, "image");
        g.url = defaultValueString(d, "url");

        return g;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("gid", getId())
                .add("games", getName())
                .build();
    }

    public JsonObject toJSONRank() {
        return Json.createObjectBuilder()
                .add("gid", getId())
                .add("games", getName())
                .add("ranking", getRanking())
                .build();
    }

    public JsonObject toJSONById() {
        return Json.createObjectBuilder()
                .add("gid", getId())
                .add("games", getName())
                .add("ranking", getRanking())
                .add("year", getYear())
                .add("users_rated", getUserRated())
                .add("url", getUrl())
                .add("thumbnail", getThumbnail())
                .add("timestamp", new Date().toString())
                .build();
    }

    // need to handle null values
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
