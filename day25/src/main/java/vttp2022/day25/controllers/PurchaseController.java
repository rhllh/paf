package vttp2022.day25.controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
    
    @PostMapping
    public String postTicketForm(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        String name = form.getFirst("name");
        Integer numTix = Integer.parseInt(form.getFirst("numTickets"));

        String orderId = UUID.randomUUID().toString().substring(0,8);

        model.addAttribute("name", name);
        model.addAttribute("numTickets", numTix);
        model.addAttribute("orderId", orderId);

        return "purchases";
    }
}
