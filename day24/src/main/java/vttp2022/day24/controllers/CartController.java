package vttp2022.day24.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp2022.day24.models.OrderItem;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    @PostMapping
    public String postForm(@RequestBody MultiValueMap<String, String> form, 
                           Model model, HttpSession session) {

        String sessionId = session.getId();

        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("cart");
        if (orderItems == null) {
            System.out.println("This is a new session");
            System.out.println("Session ID > " + sessionId);
            System.out.println("-----------------------------------");
            orderItems = new LinkedList<>();
            session.setAttribute("cart", orderItems);
        }

        String itemName = form.getFirst("item");
        int quantity = Integer.parseInt(form.getFirst("quantity"));

        // todo: check that item exists in cart - modify quantity
        orderItems.add(new OrderItem(itemName, quantity));
        session.setAttribute("cart", orderItems);
        
        model.addAttribute("orderItems", orderItems);

        return "cart";
    }
}
