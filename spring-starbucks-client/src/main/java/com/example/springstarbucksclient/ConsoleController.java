package com.example.springstarbucksclient;

import com.example.springstarbucksclient.model.Card;
import com.example.springstarbucksclient.model.Order;
import com.example.springstarbucksclient.model.Ping;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.HashMap;
import java.util.Map;

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

    public String drink = "-";
    public String milk = "-";
    public String size = "-";

    @GetMapping
    public String getAction(@ModelAttribute("command") ConsoleCommand command,
                            Model model) {
        return "console";
    }

    @PostMapping
    public String postAction(@ModelAttribute("command") ConsoleCommand command,
                             @RequestParam(value = "action", required = true) String action,
                             Errors errors, Model model, HttpServletRequest request) throws JsonProcessingException {

        System.out.println( command );

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String resourceUrl = "" ;
        String message = "";

        // Set API Key Header
        headers.set( "apikey", "2742a237475c4703841a2bf906531eb0" ) ;
        //2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ
        if (action.equals("PING")) {
            message = "PING";
            resourceUrl = "http://35.232.250.104:80/api/ping?apikey={apikey}";
            // get response as string
            try {
            ResponseEntity<String> stringResponse = restTemplate.getForEntity(resourceUrl, String.class, "2742a237475c4703841a2bf906531eb0" );
            message = stringResponse.getBody();
            // get response as POJO
            ResponseEntity<Ping> pingResponse = restTemplate.getForEntity(resourceUrl, Ping.class, "2742a237475c4703841a2bf906531eb0");
            Ping pingMsg = pingResponse.getBody();
            System.out.println( pingMsg );
            ObjectMapper objectMapper = new ObjectMapper() ;
            Object object = objectMapper.readValue(message, Object.class);
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(jsonString) ;
            message = "\n" + jsonString.substring(2, jsonString.length() - 2);
            }
            catch ( Exception e ) {
                message = "  Faile to Ping, Please Try Again!";
            }
        }
        if (action.equals("NEW CARD")) {
            message = "";
            resourceUrl = "http://35.232.250.104:80/api/cards";
            try {
            // get response as POJO
            String emptyRequest = "" ;
            HttpEntity<String> newCardRequest = new HttpEntity<String>(emptyRequest, headers) ;
            ResponseEntity<Card> newCardResponse = restTemplate.postForEntity(resourceUrl, newCardRequest, Card.class);
            Card newCard = newCardResponse.getBody();
            System.out.println( newCard );
            ObjectMapper objectMapper = new ObjectMapper() ;
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newCard);
            System.out.println( jsonString) ;
            message = "\n" + jsonString.substring(2, jsonString.length() - 2);
            }
            catch ( Exception e ) {
                message = "  Faile to Create a New Card, Please Try Again!";
            }
        }
        if (action.equals("NEW ORDER")) {
            message = "";
            resourceUrl = "http://35.232.250.104:80/api/order/register/5012349";
            try {
            // get response as POJO
            Order orderRequest = new Order() ;
            orderRequest.setDrink("Caffe Latte") ;
            orderRequest.setMilk("Whole Milk") ;
            orderRequest.setSize("Grande");
            HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(orderRequest,headers) ;
            ResponseEntity<Order> newOrderResponse = restTemplate.postForEntity(resourceUrl, newOrderRequest, Order.class);
            Order newOrder = newOrderResponse.getBody();
            System.out.println( newOrder );
            ObjectMapper objectMapper = new ObjectMapper() ;
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newOrder);
            System.out.println( jsonString) ;
            message = "New Order！！";
            message += "\n" + jsonString.substring(2, jsonString.length() - 2);
            drink = "-";
            milk = "-";
            size = "-";
            }
            catch ( Exception e ) {
                message = "  Alreadly Have a Order！！";
                try {
                    resourceUrl = "http://35.232.250.104:80/api/order/register/5012349?apikey={apikey}";
                    ResponseEntity<Order> stringResponse = restTemplate.getForEntity(resourceUrl, Order.class, "2742a237475c4703841a2bf906531eb0");
                    Order newOrder = stringResponse.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newOrder);
                    message += "\n" + jsonString.substring(2, jsonString.length() - 2);
                }
                catch ( Exception e2 ) {}
            }
        }
        if (action.equals("ACTIVATE CARD")) {
            message = "";
            resourceUrl = "http://35.232.250.104:80/api/card/activate/"+command.getCardnum()+"/"+command.getCardcode();
            try {
            // get response as POJO
            String emptyRequest = "" ;
            HttpEntity<String> newCardRequest = new HttpEntity<String>(emptyRequest,headers) ;
            ResponseEntity<Card> newCardResponse = restTemplate.postForEntity(resourceUrl, newCardRequest, Card.class);
            Card newCard = newCardResponse.getBody();
            System.out.println( newCard );
            ObjectMapper objectMapper = new ObjectMapper() ;
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newCard);
            System.out.println( jsonString) ;
            message = "\n" + jsonString.substring(2, jsonString.length() - 2);
            }
            catch ( Exception e ) {
                message = "  Faile to Activete The Card, Please Makesure Enter Card Number and Code!";
            }
        }
        if (action.equals("PAY")) {
            message = "";
            resourceUrl = "http://35.232.250.104:80/api/order/register/5012349/pay/"+command.getCardnum() ;
            System.out.println(resourceUrl) ;
            // get response as POJO
            if(!command.getCardnum().equals("admin")){
                try {
                    String emptyRequest = "" ;
                    HttpEntity<String> paymentRequest = new HttpEntity<String>(emptyRequest,headers) ;
                    ResponseEntity<Card> payForOrderResponse = restTemplate.postForEntity(resourceUrl, paymentRequest, Card.class);
                    Card orderPaid = payForOrderResponse.getBody();
                    System.out.println( orderPaid );
                    ObjectMapper objectMapper = new ObjectMapper() ;
                    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderPaid);
                    System.out.println( jsonString) ;
                    message = "\n" + jsonString.substring(2, jsonString.length() - 2);
                }
                catch ( Exception e ) {
                    message = "  Faile to Pay The Bill, Please Makesure Enter Card Number and Code!";
                }
            }
            else{
                message = "\n\n  Admin mode";
            }

        }
        if (action.equals("Caffe Latte")) {
            drink = "Caffe Latte";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("Caffe Americano")) {
            drink = "Caffe Americano";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("Caffe Mocha")) {
            drink = "Caffe Mocha";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("Whole Milk")) {
            milk = "Whole Milk";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("2% Milk")) {
            milk = "2% Milk";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("Soy Milk")) {
            milk = "Soy Milk";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("Tall")) {
            size = "Tall";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("Grande")) {
            size = "Grande";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("Venti")) {
            size = "Venti";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }
        if (action.equals("Clear")) {
            drink = "-";
            milk = "-";
            size = "-";
            message = "\n\n  Your order:\n  Drink: " + drink + "\n  Milk: " + milk + "\n  Size:" + size;
        }

        model.addAttribute("message", message);
        return "console";
    }

}