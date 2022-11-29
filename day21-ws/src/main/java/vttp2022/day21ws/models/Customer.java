package vttp2022.day21ws.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Customer {
    private String lastName;
    private String firstName;
    private String stateProvince;
    
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getStateProvince() {
        return stateProvince;
    }
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public static Customer create(SqlRowSet rs) {
        Customer c = new Customer();
        c.setFirstName(rs.getString("first_name"));
        c.setLastName(rs.getString("last_name"));
        c.setStateProvince(rs.getString("state_province"));

        return c;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("firstName",getFirstName())
            .add("lastName",getLastName())
            .add("stateProvince",getStateProvince())
            .build();
    }

}
