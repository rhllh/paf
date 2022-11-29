package vttp2022.day21ws.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.day21ws.models.Customer;
import vttp2022.day21ws.models.Order;

import static vttp2022.day21ws.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class CustomerRepo {
    
    @Autowired
    private JdbcTemplate template;

    public List<Customer> getCustomersByLimitOffset(Integer limit, Integer offset) {

        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CUSTOMERS_LIMIT_OFFSET, limit, offset);
        final List<Customer> customers = new LinkedList<>();

        while (rs.next()) {
            Customer c = Customer.create(rs);
            customers.add(c);
        }

        return customers;
    }

    public Customer getCustomerById(String id) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CUSTOMER_BY_ID, id);

        Customer c = new Customer();
        if (!rs.next()) {
            return null;
        } else {
            c = Customer.create(rs);
        }

        return c;
    }

    public List<Order> getCustomerOrdersById(String id) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CUSTOMER_ORDER_BY_ID, id);

        final List<Order> orders = new LinkedList<>();

        while (rs.next()) {
            Order o = Order.create(rs);
            orders.add(o);
        }

        return orders;
    }
}
