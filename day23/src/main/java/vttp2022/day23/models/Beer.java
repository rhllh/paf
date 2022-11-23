package vttp2022.day23.models;

import java.util.Date;

public class Beer {
    
    private int id;
    private int breweryId;
    private String name;
    private int catId;
    private int styleId;
    private float abv;
    private float ibu;
    private float srm;
    private int upc;
    private String filepath;
    private String descript;
    private int add_user;
    private Date lastMod;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getBreweryId() {
        return breweryId;
    }
    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCatId() {
        return catId;
    }
    public void setCatId(int catId) {
        this.catId = catId;
    }
    public int getStyleId() {
        return styleId;
    }
    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }
    public float getAbv() {
        return abv;
    }
    public void setAbv(float abv) {
        this.abv = abv;
    }
    public float getIbu() {
        return ibu;
    }
    public void setIbu(float ibu) {
        this.ibu = ibu;
    }
    public float getSrm() {
        return srm;
    }
    public void setSrm(float srm) {
        this.srm = srm;
    }
    public int getUpc() {
        return upc;
    }
    public void setUpc(int upc) {
        this.upc = upc;
    }
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public String getDescript() {
        return descript;
    }
    public void setDescript(String descript) {
        this.descript = descript;
    }
    public int getAdd_user() {
        return add_user;
    }
    public void setAdd_user(int add_user) {
        this.add_user = add_user;
    }
    public Date getLastMod() {
        return lastMod;
    }
    public void setLastMod(Date lastMod) {
        this.lastMod = lastMod;
    }

    
}
