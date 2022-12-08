package vttp2022.day29.models;

import jakarta.json.JsonObject;

public class Url {
    private String type;
    private String url;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public static Url create(JsonObject urlObj) {
        Url u = new Url();

        u.type = urlObj.getString("type");
        if (u.type == "") {
            return new Url();
        }
        u.url = urlObj.getString("url");

        return u;
    }
}
