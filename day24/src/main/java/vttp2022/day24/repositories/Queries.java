package vttp2022.day24.repositories;

public class Queries {
    
    public static final String SQL_INSERT_PURCHASE_ORDER = 
        "insert into purchase_order(order_id, name, order_date) values(?, ?, ?)";

    public static final String SQL_INSERT_ORDER_ITEM = 
        "insert into order_item(description, quantity, order_id) values(?, ?, ?)";
}
