package vttp2022.day21ws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2022.day21ws.models.Customer;
import vttp2022.day21ws.models.Order;
import vttp2022.day21ws.repositories.CustomerRepo;

@RestController
@RequestMapping(path="/api")
public class CustomerRESTController {
    
    @Autowired
    private CustomerRepo cRepo;

    @GetMapping(path="/customers",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomers(@RequestParam(value="limit",required=false,defaultValue="5") Integer limit,
                                               @RequestParam(value="offset",required=false,defaultValue="0") Integer offset) {
        System.out.println("limit > " + limit);
        System.out.println("offset > " + offset);

        // insert values into select statement
        List<Customer> customers = cRepo.getCustomersByLimitOffset(limit, offset);
        
        JsonArrayBuilder jarrayb = Json.createArrayBuilder();
        for (Customer c : customers) {
            jarrayb.add(c.toJSON());
        }
        JsonArray result = jarrayb.build();
        
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path="/customer/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomerById(@PathVariable("id") String id) {
        System.out.println("customer id > " + id);

        // insert values into select statement
        Customer c = cRepo.getCustomerById(id);
        if (c == null) {
            System.out.println("no customer with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No customer found with id %s",id));
        }
        JsonObject result = c.toJSON();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());

    }

    @GetMapping(path="/customer/{id}/orders",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomerOrders(@PathVariable("id") String id) {
        System.out.println("customer id > " + id);

        // insert values into select statement
        List<Order> orders = cRepo.getCustomerOrdersById(id);

        if (orders.size() == 0) {
            System.out.println("no orders for customer id > " + id);
            return ResponseEntity.status(HttpStatus.OK).body(String.format("No orders for customer id %s", id));
        }
        JsonArrayBuilder jarrayb = Json.createArrayBuilder();
        for (Order o : orders) {
            jarrayb.add(o.toJSON());
        }
        JsonArray result = jarrayb.build();
        
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }
}
