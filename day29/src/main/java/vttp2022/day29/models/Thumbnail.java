package vttp2022.day29.models;

import jakarta.json.JsonObject;

public class Thumbnail {
    private String path;
    private String extension;

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }

    public static Thumbnail create(JsonObject arr) {
        Thumbnail t = new Thumbnail();

        t.path = arr.getString("path");
        if (t.path == "image_not_available") {
            return new Thumbnail();
        }
        t.extension = arr.getString("extension");

        return t;
    }
}
