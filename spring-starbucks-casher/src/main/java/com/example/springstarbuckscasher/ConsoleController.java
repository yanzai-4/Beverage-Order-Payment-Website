package com.example.springstarbuckscasher;

import com.example.springstarbuckscasher.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

/*
    RestTemplate JavaDoc:
        * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html
        * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html

    Tutorial Resources:
        * https://reflectoring.io/spring-resttemplate
        * https://www.baeldung.com/rest-template
        * https://springframework.guru/enable-pretty-print-of-json-with-jackson
        * https://attacomsian.com/blog/spring-boot-resttemplate-get-request-parameters-headers

 */

@Controller
@RequestMapping("/")
public class ConsoleController {
    ArrayList<String> DRINK_OPTIONS = new ArrayList<String>(Arrays.asList("Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino"));;
    ArrayList<String> MILK_OPTIONS = new ArrayList<String>(Arrays.asList("Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk"));;
    ArrayList<String> SIZE_OPTIONS = new ArrayList<String>(Arrays.asList("Short", "Tall", "Grande", "Venti", "Your Own Cup"));;
    @GetMapping
    public String getEvent(@ModelAttribute("command") ConsoleCommand command,
                            Model model) {
        return "console";
    }

    @PostMapping
    public String postEvent(@ModelAttribute("command") ConsoleCommand command,
                             @RequestParam(value = "event", required = true) String action,
                             @RequestParam(value = "stores", required = true) String id,
                             Errors errors, Model model, HttpServletRequest request) {

        System.out.println( command );

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String resourceUrl = "" ;
        String message = "";

        // Set API Key Header
        headers.set( "apikey", "2742a237475c4703841a2bf906531eb0" ) ;

        if (action.equals("Get Order")) {
            resourceUrl = "http://35.232.250.104:80/api/order/register/" + id + "?apikey={apikey}";
            // get response as string
            String emptyRequest = "";
            message = "\n";
            message += "  Starbucks Reserved Order";
            message += "\n";
            message += "\n  ****** GET ******";
            try {
                ResponseEntity<Order> stringResponse = restTemplate.getForEntity(resourceUrl, Order.class, "2742a237475c4703841a2bf906531eb0");
                Order newOrder = stringResponse.getBody();
                ObjectMapper objectMapper = new ObjectMapper() ;
                String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newOrder);
                message += "\n" + jsonString.substring(2,jsonString.length()-2);
            }
            catch ( Exception e ) {
                message += "\n";
                message += "\n  No Order Found!";
            }
            message += "\n";
            message += "\n  ******************";
        }
        if (action.equals("Place Order")) {
            resourceUrl = "http://35.232.250.104:80/api/order/register/" + id;
            // get response as POJO
            message = "\n";
            message += "  Starbucks Reserved Order";
            message += "\n";
            message += "\n  ****** POST ******";
            Order orderRequest = new Order() ;
            orderRequest.setDrink(DRINK_OPTIONS.get((int)(Math.random()*3))) ;
            orderRequest.setMilk(MILK_OPTIONS.get((int)(Math.random()*3))) ;
            orderRequest.setSize(SIZE_OPTIONS.get((int)(Math.random()*3)));
            orderRequest.setTotal((Math.random()*3));
            System.out.println(orderRequest);
            HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(orderRequest,headers) ;
            try {
                ResponseEntity<Order> newOrderResponse = restTemplate.postForEntity(resourceUrl, newOrderRequest, Order.class);
                Order newOrder = newOrderResponse.getBody();
                ObjectMapper objectMapper = new ObjectMapper() ;
                String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newOrder);
                System.out.println(jsonString) ;
                message += "\n" + jsonString.substring(2,jsonString.length()-2);
            }
            catch ( Exception e ) {
                message += "\n";
                message += "\n  Alreadly have Order!";
            }
            message += "\n";
            message += "\n  ******************";
        }
        if (action.equals("Clear Order")) {
            message = "\n";
            message += "  Starbucks Reserved Order";
            message += "\n";
            message += "\n  ****** DELETE ******";
            resourceUrl = "http://35.232.250.104:80/api/order/register/" + id + "?apikey=2742a237475c4703841a2bf906531eb0";
            // get response as POJO
            Order orderRequest = new Order() ;
            HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(orderRequest,headers) ;
            try {
                restTemplate.delete(resourceUrl, Order.class, "2742a237475c4703841a2bf906531eb0");
                message += "\n";
                message += "\n  Register: " + id + " has been delete";
                message += "\n  Ready for New Order";
            }
            catch ( Exception e ) {
                message += "\n";
                message += "\n  Delete Faile!";
            }
            message += "\n";
            message += "\n  ******************";
        }
        model.addAttribute("message", message);
        return "console";
    }

}