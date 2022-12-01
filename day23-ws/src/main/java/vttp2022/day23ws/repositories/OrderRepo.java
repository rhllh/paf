package vttp2022.day23ws.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.day23ws.models.Order;

import static vttp2022.day23ws.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class OrderRepo {
    
    @Autowired
    private JdbcTemplate template;

    public Order queryOrder(String orderId) {

        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ORDER_BY_ORDERID, orderId);

        final List<Order> orders = new LinkedList<>();
        while (rs.next()) {
            Order order =  Order.create(rs);
            orders.add(order);
        }

        return orders.get(0);
    }
}
