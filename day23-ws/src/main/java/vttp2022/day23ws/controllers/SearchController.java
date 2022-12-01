package vttp2022.day23ws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.day23ws.models.Order;
import vttp2022.day23ws.repositories.OrderRepo;

@Controller
@RequestMapping("/order/total")
public class SearchController {

    @Autowired
    private OrderRepo orderRepo;
    
    // ?
    @GetMapping
    public String getOrderId(@RequestParam("orderId") String orderId, Model model) {
        System.out.println("order id > " + orderId);

        Order order = orderRepo.queryOrder(orderId);

        model.addAttribute("order", order);

        return "order";
    }
}
