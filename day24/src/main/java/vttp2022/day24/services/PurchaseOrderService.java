package vttp2022.day24.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.day24.models.OrderItem;
import vttp2022.day24.models.PurchaseOrder;
import vttp2022.day24.repositories.PurchaseOrderRepo;

@Service
public class PurchaseOrderService {

    @Autowired
    private OrderItemService oiRepo;

    @Autowired
    private PurchaseOrderRepo poRepo;
    
    @Transactional(rollbackFor = OrderException.class)
    public void createPurchaseOrder(PurchaseOrder po) throws OrderException {
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        System.out.println("orderID > " + orderId);

        System.out.println("----------------");
        for (OrderItem oi : po.getOrderItems()) {
            System.out.println(oi.getDescription());
        }

        po.setOrderId(orderId);

        poRepo.insertPurchaseOrder(po);
        oiRepo.createOrderItems(po.getOrderItems(), orderId);
    }
}
