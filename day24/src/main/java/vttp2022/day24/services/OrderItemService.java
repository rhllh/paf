package vttp2022.day24.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.day24.models.OrderItem;
import vttp2022.day24.repositories.OrderItemRepo;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepo oiRepo;
    
    public void createOrderItems(List<OrderItem> orderItems, String orderId) {

        oiRepo.insertOrderItem(orderItems, orderId);
    }
}
