package vttp2022.day23ws.repositories;

public class Queries {
    
    public static final String SQL_SELECT_ORDER_BY_ORDERID = 
        """
        select o.id as order_id, 
            DATE_FORMAT(o.order_date, \"%d/%m/%Y\") as order_date, 
            o.customer_id as customer_id, 
			sum(od.quantity * od.unit_price) as total_price,
            sum(od.quantity * od.unit_price * od.discount) as discount,
            sum(od.quantity * od.unit_price) - sum(od.quantity * od.unit_price * od.discount) as discounted_price,
            sum(od.quantity * p.standard_cost) as cost_price
        from orders o
        left join order_details od
        on o.id = od.id
        left join products p
        on od.product_id = p.id
        where o.id = ?
        """;
}
