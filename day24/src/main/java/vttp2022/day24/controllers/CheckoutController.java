package vttp2022.day24.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp2022.day24.models.OrderItem;
import vttp2022.day24.models.PurchaseOrder;
import vttp2022.day24.services.PurchaseOrderService;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private PurchaseOrderService poSvc;

    @PostMapping
    public String postCheckout(Model model, HttpSession session) {

        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("cart");
        System.out.println("Session ID > " + session.getId());
        for (OrderItem oi : orderItems) {
            System.out.println(oi.getDescription());
        }

        PurchaseOrder po = new PurchaseOrder();

        // todo: take from form
        po.setName("rahillah");
        po.setOrderItems(orderItems);
        po.setOrderDate(new Date());

        try {
            poSvc.createPurchaseOrder(po);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // destroy session
        session.invalidate();

        // todo: calculate price

        return "checkout";
    }
}
