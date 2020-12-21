package com.xinsteel.sgs.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/provincialExpressway")
public class ProvincialExpresswayController {

    @PostMapping("/sendMessage")
    public void SendMessage(){


    }
}
