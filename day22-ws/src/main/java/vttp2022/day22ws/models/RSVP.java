package vttp2022.day22ws.models;

import java.io.ByteArrayInputStream;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RSVP {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String confirmationDate;
    private String comments;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(String confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public static RSVP create(SqlRowSet rs) {
        RSVP r = new RSVP();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setEmail(rs.getString("email"));
        r.setPhone(rs.getString("phone"));
        r.setConfirmationDate(rs.getString("confirmation_date"));
        if (r.confirmationDate == null) {
            r.setConfirmationDate("");
        }
        r.setComments(rs.getString("comments"));
        if (r.comments == null) {
            r.setComments("");
        }

        return r;
    }

    public static RSVP create(String json) {
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(json.getBytes()));
        return create(reader.readObject());
    }

    public static RSVP create(JsonObject jsonObject) {
        final RSVP r = new RSVP();
        r.setName(jsonObject.getString("name"));
        r.setEmail(jsonObject.getString("email"));
        r.setPhone(jsonObject.getString("phone"));
        r.setConfirmationDate(jsonObject.getString("confirmation_date"));
        if (r.confirmationDate == null) {
            r.setConfirmationDate("");
        }
        r.setComments(jsonObject.getString("comments"));
        if (r.comments == null) {
            r.setComments("");
        }

        return r;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("id",getId())
            .add("name",getName())
            .add("email",getEmail())
            .add("phone",getPhone())
            .add("confirmationDate",getConfirmationDate())
            .add("comments",getComments())
            .build();
    }
    
}
