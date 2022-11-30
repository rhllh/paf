package vttp2022.day23.models;

import java.io.Serializable;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Brewery implements Serializable {
    
    private int id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String code;
    private String country;
    private String phone;
    private String website;
    private String filepath;
    private String descript;
    private int addUser;
    private Date lastMod;

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAddress1() {
        return address1;
    }


    public void setAddress1(String address1) {
        this.address1 = address1;
    }


    public String getAddress2() {
        return address2;
    }


    public void setAddress2(String address2) {
        this.address2 = address2;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getWebsite() {
        return website;
    }


    public void setWebsite(String website) {
        this.website = website;
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


    public int getAddUser() {
        return addUser;
    }


    public void setAddUser(int addUser) {
        this.addUser = addUser;
    }


    public Date getLastMod() {
        return lastMod;
    }


    public void setLastMod(Date lastMod) {
        this.lastMod = lastMod;
    }
    
    public static Brewery create(SqlRowSet rs) {
        Brewery b = new Brewery();
        b.setName(rs.getString("name"));

        return b;
    }
}
