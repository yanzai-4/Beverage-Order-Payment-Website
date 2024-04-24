package com.example.springpaymentsapi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Payload {

    public String toJson() {
        /* 
            https://www.baeldung.com/jackson-object-mapper-tutorial
        */
        String json = "" ;
        ObjectMapper mapper = new ObjectMapper();
        try { 
            json = mapper.writeValueAsString(this);
        } catch ( Exception e ) { }
        return json ;
    }

}
