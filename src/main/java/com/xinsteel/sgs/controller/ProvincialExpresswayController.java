package com.xinsteel.sgs.controller;

import com.xinsteel.sgs.service.impl.ProvincialExpresswayService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/provincialExpressway")
public class ProvincialExpresswayController {

    private ProvincialExpresswayService provincialExpresswayService;

    @PostMapping("/sendMessage")
    public void SendMessage(){


    }

}
