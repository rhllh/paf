package vttp2022.day23.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Style {
    
    private int id;
    private int catId;
    private String styleName;
    private String lastMod;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCatId() {
        return catId;
    }
    public void setCatId(int catId) {
        this.catId = catId;
    }
    public String getStyleName() {
        return styleName;
    }
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
    public String getLastMod() {
        return lastMod;
    }
    public void setLastMod(String lastMod) {
        this.lastMod = lastMod;
    }

    public static Style create(SqlRowSet rs) {
        Style s = new Style();
        s.setStyleName(rs.getString("style_name"));

        return s;
    }
}
