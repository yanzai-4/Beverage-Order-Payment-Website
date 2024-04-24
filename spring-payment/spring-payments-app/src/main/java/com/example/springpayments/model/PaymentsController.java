package com.example.springpayments.model;
import com.example.springpayments.model.model.AuthRequest;
import com.example.springpayments.model.model.AuthResponse;
import com.example.springpayments.model.model.CaptureRequest;
import com.example.springpayments.model.model.CaptureResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/")
public class PaymentsController {

    private static boolean DEBUG = true ;

    private static String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI" ;
    private static String text = "DestinyHERODestroyPhoenixEnforcer" ;
    private String Url = "http://34.69.55.232/api";
    int ran = (int) (Math.random() * (100));

    private static Map<String,String> months = new HashMap<>() ;
    static {
        months.put( "January", "JAN" ) ;
        months.put( "February", "FEB" ) ;
        months.put( "March", "MAR" ) ;
        months.put( "April", "APR" ) ;
        months.put( "May", "MAY" ) ;
        months.put( "June", "JUN" ) ;
        months.put( "July", "JUL" ) ;
        months.put( "August", "AUG" ) ;
        months.put( "September", "SEP" ) ;
        months.put( "October", "OCT" ) ;
        months.put( "November", "NOV" ) ;
        months.put( "December", "DEC" ) ;
    }

    private static Map<String,String> states = new HashMap<>() ;
    static {
        states.put( "AL", "Alabama" ) ;
        states.put( "AK", "Alaska" ) ;
        states.put( "AZ", "Arizona" ) ;
        states.put( "AR", "Arkansas" ) ;
        states.put( "CA", "California" ) ;
        states.put( "CO", "Colorado" ) ;
        states.put( "CT", "Connecticut" ) ;
        states.put( "DE", "Delaware" ) ;
        states.put( "FL", "Florida" ) ;
        states.put( "GA", "Georgia" ) ;
        states.put( "HI", "Hawaii" ) ;
        states.put( "ID", "Idaho" ) ;
        states.put( "IL", "Illinois" ) ;
        states.put( "IN", "Indiana" ) ;
        states.put( "IA", "Iowa" ) ;
        states.put( "KS", "Kansas" ) ;
        states.put( "KY", "Kentucky" ) ;
        states.put( "LA", "Louisiana" ) ;
        states.put( "ME", "Maine" ) ;
        states.put( "MD", "Maryland" ) ;
        states.put( "MA", "Massachusetts" ) ;
        states.put( "MI", "Michigan" ) ;
        states.put( "MN", "Minnesota" ) ;
        states.put( "MS", "Mississippi" ) ;
        states.put( "MO", "Missouri" ) ;
        states.put( "MT", "Montana" ) ;
        states.put( "NE", "Nebraska" ) ;
        states.put( "NV", "Nevada" ) ;
        states.put( "NH", "New Hampshire" ) ;
        states.put( "NJ", "New Jersey" ) ;
        states.put( "NM", "New Mexico" ) ;
        states.put( "NY", "New York" ) ;
        states.put( "NC", "North Carolina" ) ;
        states.put( "ND", "North Dakota" ) ;
        states.put( "OH", "Ohio" ) ;
        states.put( "OK", "Oklahoma" ) ;
        states.put( "OR", "Oregon" ) ;
        states.put( "PA", "Pennsylvania" ) ;
        states.put( "RI", "Rhode Island" ) ;
        states.put( "SC", "South Carolina" ) ;
        states.put( "SD", "South Dakota" ) ;
        states.put( "TN", "Tennessee" ) ;
        states.put( "TX", "Texas" ) ;
        states.put( "UT", "Utah" ) ;
        states.put( "VT", "Vermont" ) ;
        states.put( "VA", "Virginia" ) ;
        states.put( "WA", "Washington" ) ;
        states.put( "WV", "West Virginia" ) ;
        states.put( "WI", "Wisconsin" ) ;
        states.put( "WY", "Wyoming"  ) ;
    }

    private String hmac_sha256(String secretKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256") ;
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256") ;
            mac.init(secretKeySpec) ;
            byte[] digest = mac.doFinal(data.getBytes()) ;
            java.util.Base64.Encoder encoder = java.util.Base64.getEncoder() ;
            String hash_string = encoder.encodeToString(digest) ;
            return hash_string ;
        } catch (InvalidKeyException e1) {
            throw new RuntimeException("Invalid key exception while converting to HMAC SHA256") ;
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm") ;
        }
    }
    @GetMapping
    public String getAction( @ModelAttribute("command") PaymentsCommand command,
                            Model model) throws UnknownHostException {


        text += Integer.toString(ran);
        String hash_string = hmac_sha256( key, text ) ;
        InetAddress ip = InetAddress.getLocalHost() ;
        String server_ip = ip.getHostAddress() ;
        String host_name = ip.getHostName() ;
        model.addAttribute( "hash", hash_string ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

         return "creditcards" ;

    }

    @Getter
    @Setter
    class Message {
        public String msg ;
        public Message(String m) { msg = m ; }
    }

    class ErrorMessages {
        private ArrayList<Message> messages = new ArrayList<Message>() ;
        public void add( String msg ) { messages.add(new Message(msg) ) ; }
        public ArrayList<Message> getMessages() { return messages ; }
        public void print() {
            for ( Message m : messages ) {
                System.out.println( m.msg ) ;
            }
        }
    }
    public String getCardType( String cardNum ) {
        String firstNumber = cardNum.substring(0, 1) ;
        String type = "ERROR" ;
        switch ( firstNumber ) {
            case "3": type = "003" ; break ;
            case "4": type = "001" ; break ;
            case "5": type = "002" ; break ;
            case "6": type = "004" ; break ;
        }
        return type ;
    }

    private static Map<String,String> monthsMap = new HashMap<>() ;
    static {
        monthsMap.put( "January", "01" ) ;
        monthsMap.put( "February", "02" ) ;
        monthsMap.put( "March", "03" ) ;
        monthsMap.put( "April", "04" ) ;
        monthsMap.put( "May", "05" ) ;
        monthsMap.put( "June", "06" ) ;
        monthsMap.put( "July", "07" ) ;
        monthsMap.put( "August", "08" ) ;
        monthsMap.put( "September", "09" ) ;
        monthsMap.put( "October", "10" ) ;
        monthsMap.put( "November", "11" ) ;
        monthsMap.put( "December", "12" ) ;
    }

    @PostMapping
    public String postAction(@ModelAttribute("command") PaymentsCommand command,
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) throws UnknownHostException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", key);

        text += Integer.toString(ran);
        String hash_string = hmac_sha256( key, text ) ;
        InetAddress ip = InetAddress.getLocalHost() ;
        String server_ip = ip.getHostAddress() ;
        String host_name = ip.getHostName() ;
        model.addAttribute( "hash", hash_string ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        ErrorMessages msgs = new ErrorMessages();

        /* check for errors */
        boolean hasErrors = false;
        if (command.firstname().equals("")) {
            hasErrors = true;
            msgs.add("First Name Required.");
        }
        if (command.lastname().equals("")) {
            hasErrors = true;
            msgs.add("Last Name Required.");
        }
        if (command.address().equals("")) {
            hasErrors = true;
            msgs.add("Address Required.");
        }
        if (command.city().equals("")) {
            hasErrors = true;
            msgs.add("City Required.");
        }
        if (command.state().equals("")) {
            hasErrors = true;
            msgs.add("State Required.");
        }
        if (command.zip().equals("")) {
            hasErrors = true;
            msgs.add("Zip Required.");
        }
        if (command.phone().equals("")) {
            hasErrors = true;
            msgs.add("Phone Required.");
        }
        if (command.cardnum().equals("")) {
            hasErrors = true;
            msgs.add("Credit Card Number Required.");
        }
        if (command.cardexpmon().equals("")) {
            hasErrors = true;
            msgs.add("Credit Card Expiration Month Required.");
        }
        if (command.cardexpyear().equals("")) {
            hasErrors = true;
            msgs.add("Credit Card Expiration Year Required.");
        }
        if (command.cardcvv().equals("")) {
            hasErrors = true;
            msgs.add("Credit Card CVV Required.");
        }
        if (command.email().equals("")) {
            hasErrors = true;
            msgs.add("Email Address Required.");
        }

        // regex validations: https://www.vogella.com/tutorials/JavaRegularExpressions/article.html
        if (!command.zip().matches("\\d{5}")) {
            hasErrors = true;
            msgs.add("Invalid Zip Code: " + command.zip());
        }
        if (!command.phone().matches("[(]\\d{3}[)] \\d{3}-\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Phone Number: " + command.phone());
        }
        if (!command.cardnum().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Card Number: " + command.cardnum());
        }
        if (!command.cardexpyear().matches("\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Card Expiration Year: " + command.cardexpyear());
        }
        if (!command.cardcvv().matches("\\d{3}")) {
            hasErrors = true;
            msgs.add("Invalid Card CVV: " + command.cardcvv());
        }

        // validate months of the year
        if (months.get(command.cardexpmon()) == null) {
            hasErrors = true;
            msgs.add("Invalid Card Expiration Month: " + command.cardexpmon());
        }

        // validate states of 50 U.S. states
        if (states.get(command.state()) == null) {
            hasErrors = true;
            msgs.add("Invalid State: " + command.state());
        }

        /* Render View */
        if (hasErrors) {
            msgs.print();
            model.addAttribute("messages", msgs.getMessages());
            return "creditcards";
        }
        /* process payment */
        int min = 1111111;
        int max = 9999999;
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String order_num = String.valueOf(random_int);
        AuthRequest auth = new AuthRequest();
        auth.reference = order_num;
        auth.billToFirstName = command.firstname();
        auth.billToLastName = command.lastname();
        auth.billToAddress = command.address();
        auth.billToCity = command.city();
        auth.billToState = command.state();
        auth.billToZipCode = command.zip();
        auth.billToPhone = command.phone();
        auth.billToEmail = command.email();
        auth.transactionAmount = "30.00";
        auth.transactionCurrency = "USD";
        auth.cardNumnber = command.cardnum();
        auth.cardExpMonth = monthsMap.get(command.cardexpmon());
        auth.cardExpYear = command.cardexpyear();
        auth.cardCVV = command.cardcvv();
        auth.cardType = getCardType(auth.cardNumnber);
        if (auth.cardType.equals("ERROR")) {
            System.out.println("Unsupported Credit Card Type.");
            model.addAttribute("message", "Unsupported Credit Card Type.");
            return "creditcards";
        }
        boolean authValid = true;
        AuthResponse authResponse = new AuthResponse();
        System.out.println("\n\nAuth Request: " + auth.toJson());
        //authResponse = api.authorize(auth) ;
        AuthResponse newAuth = null;
        try {
            String resourceUrl = Url + "/authorize";
            HttpEntity<AuthRequest> newAuthRequest = new HttpEntity<AuthRequest>(auth, headers);
            ResponseEntity<AuthResponse> newAuthResponse = restTemplate.postForEntity(resourceUrl, newAuthRequest, AuthResponse.class);
            newAuth = newAuthResponse.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("\n\nAuth Response: " + newAuth.toJson());
            System.out.println(auth.toJson());
        } catch (Exception e) {
        }
        if (!newAuth.getStatus().equals("AUTHORIZED")) {
            authValid = false;
            System.out.println(newAuth.getMessage());
            model.addAttribute("message", newAuth.getMessage());
            return "creditcards";
        }
        boolean captureValid = true;
        CaptureRequest capture = new CaptureRequest();
        CaptureResponse captureResponse = new CaptureResponse();
        CaptureResponse newCapture = null;
        if (authValid) {
            capture.reference = order_num;
            capture.paymentId = newAuth.getId();
            capture.transactionAmount = "30.00";
            capture.transactionCurrency = "USD";
            System.out.println("\n\nCapture Request: " + capture.toJson());
            //captureResponse = api.capture(capture) ;
            try {
                String resourceUrl = Url + "/capture";
                HttpEntity<CaptureRequest> newCaptureRequest = new HttpEntity<CaptureRequest>(capture, headers);
                ResponseEntity<CaptureResponse> newCaptureResponse = restTemplate.postForEntity(resourceUrl, newCaptureRequest, CaptureResponse.class);
                newCapture = newCaptureResponse.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newCapture);
                System.out.println("\n\nCapture Response: " + newCapture.toJson());
                System.out.println(newCapture.toJson());
            } catch (Exception e) {
            }
            if (!newCapture.getStatus().equals("PENDING")) {
                captureValid = false;
                System.out.println(newCapture.getMessage());
                model.addAttribute("message", newCapture.getMessage());
                return "creditcards";
            }
        }

        /* Render View */
        if (authValid && captureValid) {

            command.setOrderNumber(order_num);
            command.setTransactionAmount("30.00");
            command.setTransactionCurrency("USD");
            command.setAuthId(newAuth.getId());
            command.setAuthStatus(newAuth.getStatus());
            command.setCaptureId(newCapture.getId());
            command.setCaptureStatus(newCapture.getStatus());

            /* Send back Status */
            System.out.println("\n\nThank You for your Payment! Your Order Number is: " + order_num + "\nYour Transaction ID is: " + newCapture.getReconciliationId());
            model.addAttribute("message", "Thank You for your Payment! Your Order Number is: " + order_num + " & Transaction ID: " + newCapture.getReconciliationId());
        }

        return "creditcards";
    }

}













