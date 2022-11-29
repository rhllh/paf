package vttp2022.day21ws.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {
    private String name;
    private String orderDate;
    private String shipAddress;
    private String shipCity;
    private Integer orderId;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getShipAddress() {
        return shipAddress;
    }
    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }
    public String getShipCity() {
        return shipCity;
    }
    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public static Order create(SqlRowSet rs) {
        Order o = new Order();
        o.setName(rs.getString("ship_name"));
        o.setOrderDate(rs.getString("order_date"));
        o.setShipAddress(rs.getString("ship_address"));
        o.setShipCity(rs.getString("ship_city"));
        o.setOrderId(rs.getInt("id"));

        return o;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("name",getName())
            .add("orderID",getOrderId())
            .add("orderDate",getOrderDate())
            .add("shipAddress",getShipAddress())
            .add("shipCity",getShipCity())
            .build();
    }

}
