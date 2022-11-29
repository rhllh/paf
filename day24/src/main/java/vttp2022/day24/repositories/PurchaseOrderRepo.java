package vttp2022.day24.repositories;

import static vttp2022.day24.repositories.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.day24.models.PurchaseOrder;

@Repository
public class PurchaseOrderRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insertPurchaseOrder(PurchaseOrder po) {
        
        return jdbcTemplate.update(SQL_INSERT_PURCHASE_ORDER, po.getOrderId(), po.getName(), po.getOrderDate()) > 0;
    }
}
