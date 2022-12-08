package vttp2022.day29.models;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class Character {
    private String name;
    private String description;
    private String thumbnail;
    private String url;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public static Character create(JsonObject obj) {
        final Character c = new Character();

        c.setName(obj.getString("name"));
        c.setDescription(obj.getString("description").trim().length() > 0 ? obj.getString("description") : "No description");
        JsonObject img = obj.getJsonObject("thumbnail");
        c.setThumbnail("%s.%s".formatted(img.getString("path"), img.getString("extension")));
        JsonArray urls = obj.getJsonArray("urls");
        for (Integer i = 0; i < urls.size(); i++) {
            JsonObject d = urls.getJsonObject(i);
            if (d.getString("type").equals("detail")) {
                c.setUrl(d.getString("url"));
                break;
            }
        }
        return c;
    }

    public static Character fromCache(JsonObject j) {
        final Character c = new Character();

        c.setName(j.getString("name"));
        c.setDescription(j.getString("description"));
        c.setThumbnail(j.getString("thumbnail"));
        c.setUrl(j.getString("url"));

        return c;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                    .add("name", name)
                    .add("description", description)
                    .add("thumbnail", thumbnail)
                    .add("url", url)
                    .build();
    }

}
