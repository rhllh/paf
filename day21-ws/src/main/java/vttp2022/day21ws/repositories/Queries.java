package vttp2022.day21ws.repositories;

public class Queries {
    
    public static final String SQL_SELECT_CUSTOMERS_LIMIT_OFFSET = 
        "select * from customers limit ? offset ? ";

    public static final String SQL_SELECT_CUSTOMER_BY_ID = 
        "select * from customers where id = ? ";

    public static final String SQL_SELECT_CUSTOMER_ORDER_BY_ID = 
        "select * from orders join customers on orders.customer_id = customers.id where customers.id = ? ";
}
