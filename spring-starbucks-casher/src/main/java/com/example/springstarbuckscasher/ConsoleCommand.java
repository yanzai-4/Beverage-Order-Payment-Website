package com.example.springstarbuckscasher;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class ConsoleCommand {

    private String event ;
    private String stores ;
    private String message ;

}

