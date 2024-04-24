package com.example.springpaymentsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Payments")
@Data
@RequiredArgsConstructor
public class AuthRequest extends Payload {

    private @Id @GeneratedValue @JsonIgnore Long id;

    public String reference ;
    public String billToFirstName ;
    public String billToLastName ;
    public String billToAddress ;
    public String billToZipCode ;
    public String billToCity ;
    public String billToState ;
    public String billToPhone ;
    public String billToEmail ;
    public String transactionAmount ;
    public String transactionCurrency ;
    public String cardNumnber ;
    public String cardCVV ;
    public String cardExpMonth ;
    public String cardExpYear ;
    public String cardType ;

}


