package com.alammar.apache.camel.errorHandling.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.engine.DefaultProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    private CamelContext camelContext;

    @GetMapping
    public String testCreateOrder() {

        ProducerTemplate template = new DefaultProducerTemplate(camelContext);
        template.start();
        try {
            // create sample order ID
            template.sendBody("direct:createOrder", UUID.randomUUID().toString());
            return "Success";
        } catch (Exception e) {
            return "Error ." + e.getMessage();
        }
    }
}
