package com.example.springpaymentsapi.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CaptureRequest extends Payload {

    public String reference ;
    public String paymentId ;
    public String transactionAmount ;
    public String transactionCurrency ;

}

