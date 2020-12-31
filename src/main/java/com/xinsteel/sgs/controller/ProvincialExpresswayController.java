package com.xinsteel.sgs.controller;

import com.xinsteel.sgs.service.impl.ProvincialExpresswayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/provincialExpressway")
public class ProvincialExpresswayController {

    @Autowired
    private ProvincialExpresswayServiceImpl provincialExpresswayService;

    @GetMapping("/sendMessage")
    public void SendMessage(){
        provincialExpresswayService.sendMessage();
    }
    public String GetToken(){
        return null;
    }
}
