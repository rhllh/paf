package vttp2022.day24.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.day24.models.OrderItem;
import vttp2022.day24.models.PurchaseOrder;

import static vttp2022.day24.repositories.Queries.*;

import java.util.List;

@Repository
public class OrderItemRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertOrderItem(PurchaseOrder po) {
        insertOrderItem(po.getOrderItems(), po.getOrderId());
    }
    
    public void insertOrderItem(List<OrderItem> orderItems, String orderId) {
        List<Object[]> data = orderItems.stream()
                .map(li -> {
                    Object[] l = new Object[3];
                    l[0] = li.getDescription();
                    l[1] = li.getQuantity();
                    l[2] = orderId;
                    return l;
                }).toList();

        jdbcTemplate.batchUpdate(SQL_INSERT_ORDER_ITEM, data);
    }
}
