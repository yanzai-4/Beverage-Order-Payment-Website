package com.example.springpaymentsapi.rest;

import com.example.springpaymentsapi.model.AuthRequest;
import com.example.springpaymentsapi.model.AuthResponse;
import com.example.springpaymentsapi.model.CaptureRequest;
import com.example.springpaymentsapi.model.CaptureResponse;
import com.example.springpaymentsapi.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/* See:  https://spring.io/guides/gs/rest-service/ */

@RestController
public class PaymentsController {

    @Autowired private PaymentsService service ;

    @PostMapping("/authorize")
    @ResponseStatus(HttpStatus.CREATED)
    AuthResponse getAuthorize(@RequestBody AuthRequest Request) {
        return service.authorize(Request) ;
    }

    @PostMapping("/capture")
    @ResponseStatus(HttpStatus.CREATED)
    CaptureResponse getCapture(@RequestBody CaptureRequest Request) {
        return service.capture(Request) ;
    }
}